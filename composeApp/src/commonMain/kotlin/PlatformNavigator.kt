interface PlatformNavigator {
     fun startAuth0Login(connectionType: String)
     fun startAuth0Signup(connectionType: String)
     fun startAuth0Logout(connectionType: String)
 }