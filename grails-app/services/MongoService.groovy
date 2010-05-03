import org.springframework.beans.factory.InitializingBean
import com.mongodb.BasicDBObject

/**
 * Some convenient methods for manipulating the mongo database/collections
 * 
 * In grails shell, do <code>msrv = ctx.getBean("mongoService")</code>. 
 */
class MongoService implements InitializingBean {
	def mongo
	
	void afterPropertiesSet() {
		assert mongo
		MongoUtils.decorateCollection mongo.projects
	}
	
	def hasPrj = {
		println "has data: ${mongo.projects.find().hasNext()}"
	}
	
	def bootStrap = {
		def jack = new User(username: "Jack")
		def paul = new User(username: "Paul")
		def william = new User(username: "William")
		def proj = new Project(name: "testPrj1", leader: jack)
		proj.addToDevelopers(paul)
		proj.addToDevelopers(william)
		mongo.projects.save proj.toMongoDoc()
		
		proj = new Project(name: "testPrj2", leader: paul)
		proj.addToDevelopers(jack)
		proj.addToDevelopers(william)
		mongo.projects.save proj.toMongoDoc()
		
		proj = new Project(name: "testPrj3")
		mongo.projects.save proj.toMongoDoc()
		
		proj = new Project(name: "testPrj4", leader: william)
		mongo.projects.save proj.toMongoDoc()
	}
	
	def drop = {
		mongo.projects.drop()
	}
	
	def loadObjs = {
		def docs = mongo.projects.find()
		docs.toList().collect{ println it; it.toObject()
		}
	}
	
	def showPrjMapper = {
		def mapper = mongo.getMapperForClass(Project) 
		def developersField = mapper.fields.find { it.mongoFieldName == "developers"
		}
		println "$developersField hasMany=$developersField.isGrailsHasMany"
	}
	
	def findByDeveloper = { name ->
		mongo.projects.find(["developers.username": name] as com.mongodb.BasicDBObject)
		.each {println it.toObject()
		}
	}
	
	def dynFinderExample = {
		def r = mongo.projects.findByName(["testPrj1"])
		def p = r.next().toObject()
		assert p.name == "testPrj1"
		
		assert 2 == mongo.projects.findByDevelopers_Username(["William"]).count()
	}
	
	def fmap = """
		function() {
			if (this.developers) {
				this.developers.forEach(
					function(d) {
						emit(d["username"], {count: 1});
					}
				);
			}
		}
				"""
	def freduce = """
		function(key, vals) {
			var count = 0;
			for (var i = 0; i < vals.length; i++) {
				count += vals[i].count;
			}
			return {count: count};
		}
				"""
	def mapReduceCountProjectsByDeveloper = {
		def result = mongo.projects.mapReduce(fmap, freduce, null, ["_t" : "project"] as BasicDBObject).results()
		//result.each {print it}
		result
	}
}