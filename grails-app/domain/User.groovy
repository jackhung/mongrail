import grails.mongo.MongoRef
import grails.mongo.MongoMapped

@MongoMapped("users")
class User {
  String _id
  static transcient = ["_id"]
  String username
  @MongoRef User father
  @MongoRef User mother
  ContactInfo email
  ContactInfo mobile

  static mongoTypeName = "user"
  static mongoFields = [
	"username" : "username", 
	"father" : "father", 
	"mother": "mother",
	"email" : "email",
	"mobile" : "mobile"
		]
 
  String toString() {
    "$username"
  }
}
