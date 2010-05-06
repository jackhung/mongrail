
import org.codehaus.groovy.grails.commons.ApplicationHolder
import com.mongodb.BasicDBObject
import com.mongodb.ObjectId
import com.mongodb.DBRef

description """
  Mongo mapping tests: <b>MongoRef</b> and as embedded Document
"""

scenario "Users can have DBRef to other users", {
	given "3 Users", {
		inject "mongo"
		mongo.users.drop()
		userName = "William"
		fatherName = "Pete"
		motherName = "Mary"
		mongo.users.save(new User(username: userName).toMongoDoc())
		mongo.users.save(new User(username: fatherName).toMongoDoc())
		mongo.users.save(new User(username: motherName).toMongoDoc())
	}
	when "William's father is Pete, and mother is Mary", {
		william = mongo.users.findOne([username: userName] as BasicDBObject).toObject()
		william.father = mongo.users.findOne([username: fatherName] as BasicDBObject).toObject()
		william.mother = mongo.users.findOne([username: motherName] as BasicDBObject).toObject()
		mongo.users.update([_id: new ObjectId(william._id)] as BasicDBObject, william.toMongoDoc())
	}
	then "we should have 3 users", {
		mongo.users.count.shouldEqual 3
	}
	and "william's father and mother is collectly associated", {
		william = mongo.users.findOne([username: userName] as BasicDBObject).toObject()
		william.father.username.shouldEqual fatherName
		william.mother.username.shouldEqual motherName
	}
	and "william's father and mother are RBRef", {
		william = mongo.users.findOne([username: userName] as BasicDBObject)
		assert william.father instanceof DBRef
		assert william.mother instanceof DBRef
	}
}
