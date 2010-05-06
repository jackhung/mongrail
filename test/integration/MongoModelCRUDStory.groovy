
import com.mongodb.BasicDBObject

description """
Simple test for injected mongo[CRUD] of Model functions
"""

scenario "insert -> update -> remove project instance", {
	
	given "the mongo bean", {
		inject "mongo"
		projectName = "testCRUD"
		tmp = mongo.projects.findOne([name: projectName] as BasicDBObject)
		if (tmp)
			mongo.projects.remove([_id: tmp._id] as BasicDBObject)
	}
	when "create the project", {
		prj = new Project(name: projectName)
		prj.mongoInsert(mongo.projects)
	}
	then "getting it back by it id and project name", {
		prj2 = mongo.projects.findOne([name: projectName] as BasicDBObject)
		prj2.shouldNotBe null
	}
	then "byMongoId should works"
	then "change someting"
	then "we can delete it", {
		prj2.toObject().mongoRemove(mongo.projects)
	}
	and "it should be gone...", {
		mongo.projects.findOne([name: projectName] as BasicDBObject).shouldBe null
	}
}

