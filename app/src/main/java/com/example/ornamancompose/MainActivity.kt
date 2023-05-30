package com.example.ornamancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.ui.component.BottomNav
import com.example.ornamancompose.ui.component.InputText
import com.example.ornamancompose.ui.navigation.Screen
import com.example.ornamancompose.ui.screen.LoginScreen
import com.example.ornamancompose.ui.screen.RegisterScreen
import com.example.ornamancompose.ui.screen.ScanScreen
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrnamanComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrnamanApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrnamanApp() {
   val navController = rememberNavController()
   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentRoute = navBackStackEntry?.destination?.route

   val authViewModel : AuthViewModel = viewModel(
       factory = ViewModelFactory.getInstance()
   )

    Scaffold(
        bottomBar = {
            if(
                currentRoute != "auth_screen" &&
                currentRoute != Screen.Login.route &&
                currentRoute != Screen.Register.route
            ){
                BottomNav(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    selected = {route ->
                        currentRoute == route
                    },
                    onItemClick = {route ->
                        navController.navigate(route){
                            popUpTo(Screen.Home.route){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            // Got to check if the user already logged in, by changing the start destination to home_screen if yes and auth_screen otherwise
            startDestination = "auth_screen"
        ){
            navigation(
                route = "home_screen",
                startDestination = Screen.Home.route
            ){
                composable(
                    route = Screen.Home.route
                ){
                    Text(
                        modifier = Modifier
                            .padding(innerPadding),
                        text = Screen.Home.route
                    )
                }
                composable(
                    route = Screen.Scan.route
                ){
                    ScanScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                composable(
                    route = Screen.Profile.route
                ){
                    Text(
                        modifier = Modifier
                            .padding(innerPadding),
                        text = Screen.Profile.route
                    )
                }
            }
            navigation(
                route = "auth_screen",
                startDestination = Screen.Login.route
            ){
                composable(route = Screen.Login.route){
                    LoginScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        onSignUpClick = {
                            navController.navigate(Screen.Register.route){
                                launchSingleTop = true
                            }
                        },
                        viewModel = authViewModel,
                        onSuccessLogin = {
                            navController.navigate("home_screen"){
                                popUpTo(navController.graph.findStartDestination().id){
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(route = Screen.Register.route){
                    val navigateToLogin : () -> Unit = {
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Login.route){
                                inclusive = true
                            }
                        }
                    }
                    RegisterScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        onSignInClick = navigateToLogin,
                        viewModel = authViewModel,
                        onSuccessRegister = navigateToLogin
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrnamanAppPreview() {
    OrnamanComposeTheme {
        OrnamanApp()
    }
}