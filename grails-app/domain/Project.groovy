class Project {
  String _id
  static transcient = ["_id"]

  String name
  User leader
  List<User> developers
  
  static hasMany = [developers : User]

  static mongoTypeName = "project"
  static mongoFields = ["name" : "name", "leader" : "leader", "developers" : "developers"]

  String toString() {
    "$name"
  }
}
