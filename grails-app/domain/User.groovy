class User {
  String _id
  static transcient = ["_id"]
  String username

  static mongoTypeName = "user"
  static mongoFields = ["username" : "username"]

  String toString() {
    "$username"
  }
}
