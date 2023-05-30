package com.example.ornamancompose.ui.navigation


sealed class Screen(val route : String) {
    object Home : Screen("Home")
    object Scan : Screen("Scan")
    object Profile : Screen("Profile")
    object Login : Screen("Login")
    object Register : Screen("Register")
}