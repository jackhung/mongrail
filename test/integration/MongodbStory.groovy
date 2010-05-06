
import org.codehaus.groovy.grails.commons.ApplicationHolder
	
description """
  Basic features support by the MongoDB integration, in
particular those offered by <b>MongoDbWrapper</b>, the "mongo" bean,
and <b>MongoService</b>, the "mongoService" bean.
"""

scenario "mongo should be injected", {
	
	when "requesting mongo bean", {
		//println "====>" + ApplicationHolder.application
		inject "mongo"
	}
	then "mongo should be callable", {
		assert mongo.projects.count > 0, "Project count should not be 0"
	}
}

scenario "mongoService features", {
	given "the mongoService", {
		inject "mongoService"
		username = "William"
	}
	when "requesting mapReduceCountProjectsByDeveloper", {
		developers = mongoService.mapReduceCountProjectsByDeveloper().collect {
			it.toObject()
		}
	}
	then "should return array of developers", {
		developers.size().shouldEqual 3
	}
	then "${username} is included and is developer of two projects", {
		developer = developers.find{it._id == username}
		developer.shouldNotBe null
		ensure (developer.value.count) { isEqualTo 2 }
	}
	["Jack", "Paul"].each { name ->
		and "${name} is included", {
			developer = developers.find{it._id == name}
			developer.shouldNotBe null
		}
	}
}

scenario "nested documents (e.g <b>project.leader</b>) are correctly 'unmarshal'", {
	given "leader is defined for a project document with"
	when "retrieving the project projects.find(...)"
	then "project.leader should be an instance of <b>User</b>"
}