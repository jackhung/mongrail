import grails.mongo.MongoMapped;
import grails.mongo.MongoRef;

class ContactInfo {
  String _id
  static transcient = ["_id"]
  String info

  static mongoTypeName = "contact-info"
  static mongoFields = ["info" : "info"]
 
  String toString() {
    "$info"
  }
}
