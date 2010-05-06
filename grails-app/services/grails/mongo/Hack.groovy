package grails.mongo

import org.springframework.beans.factory.InitializingBean

class Hack implements InitializingBean {
	def mongo
	
	void afterPropertiesSet() {
		assert mongo
		//MongoUtils.decorateCollection mongo.projects
	}
}
