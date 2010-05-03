
import org.codehaus.groovy.grails.commons.ApplicationHolder
	
description """
  Basic features support by the MongoDB integration, in
particular those offer by MongoDbWrapper, the "mongo" bean,
and MongoService, the "mongoService" bean.
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
	given "mongService", {
		inject "mongoService"
		username = "William"
	}
	when "requesting mapReduceCountProjectsByDeveloper", {
		developers = mongoService.mapReduceCountProjectsByDeveloper().collect {
			it.toObject()
		}
	}
	then "should return array of developers", {
		assert developers.size() == 3, "Should found 3 developers"
	}
	then "${username} is included and is developer of two projects", {
		
		developer = developers.find{it._id == username}
		assert developer, "Cannot find ${username}!"
		assert developer.value.count == 2
	}
}
