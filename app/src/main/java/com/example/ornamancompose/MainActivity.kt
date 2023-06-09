package com.example.ornamancompose

import PlantScanResponse
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.ui.component.BottomNav
import com.example.ornamancompose.ui.navigation.Screen
import com.example.ornamancompose.ui.screen.HomeScreen
import com.example.ornamancompose.ui.screen.LoginScreen
import com.example.ornamancompose.ui.screen.ProfileScreen
import com.example.ornamancompose.ui.screen.RegisterScreen
import com.example.ornamancompose.ui.screen.ScanResultScreen
import com.example.ornamancompose.ui.screen.ScanScreen
import com.example.ornamancompose.util.decodeStringUrl
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.HomeViewModel
import com.example.ornamancompose.viewmodel.ScanViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var token : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            token = intent.getStringExtra("token") ?: ""
            Log.i("MainActivity-TAG", token)
            OrnamanComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrnamanApp(token)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrnamanApp(
    token : String
) {
   val navController = rememberNavController()
   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentRoute = navBackStackEntry?.destination?.route
   val context = LocalContext.current

   val authViewModel : AuthViewModel = viewModel(
       factory = ViewModelFactory.getInstance(context)
   )
   val scanViewModel : ScanViewModel = viewModel(
       factory = ViewModelFactory.getInstance(context)
   )
   val homeViewModel : HomeViewModel = viewModel(
       factory = ViewModelFactory.getInstance(context)
   )

   val startDestination = if(token.isEmpty()) "auth_screen" else "home_screen"

    Scaffold(
        bottomBar = {
            if(
                currentRoute != "auth_screen" &&
                currentRoute != Screen.Login.route &&
                currentRoute != Screen.Register.route &&
                currentRoute != Screen.ScanResult.route
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
            startDestination = startDestination
        ){
            navigation(
                route = "home_screen",
                startDestination = Screen.Home.route
            ){
                composable(
                    route = Screen.Home.route
                ){
                    HomeScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        viewModel = homeViewModel
                    )
                }
                composable(
                    route = Screen.Scan.route
                ){
                    ScanScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        viewModel = scanViewModel
                    ){scanResult, lat, long ->
                        navController.navigate(
                            Screen.ScanResult.createRoute(scanResult, lat, long)
                        )
                    }
                }
                composable(
                    route = Screen.Profile.route
                ){
                    ProfileScreen(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        onLogout = {
                            authViewModel.clearSession()
                            navController.navigate("auth_screen"){
                                popUpTo("home_screen"){
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(
                    route = Screen.ScanResult.route,
                    arguments = listOf(
                        navArgument("kelas"){
                            type = NavType.StringType
                        },
                        navArgument("desc"){
                            type = NavType.StringType
                        },
                        navArgument("conf"){
                            type = NavType.StringType
                        },
                        navArgument("imgUrl"){
                            type = NavType.StringType
                        },
                        navArgument("lat"){
                            type = NavType.StringType
                        },
                        navArgument("long"){
                            type = NavType.StringType
                        }
                    )
                ){
                    val kelas = it.arguments?.getString("kelas") ?: ""
                    val desc = it.arguments?.getString("desc") ?: ""
                    val conf = it.arguments?.getString("conf") ?: ""
                    val imgUrl = it.arguments?.getString("imgUrl") ?: ""
                    val lat = it.arguments?.getString("lat") ?: ""
                    val long = it.arguments?.getString("long") ?: ""

                    val scanResult = PlantScanResponse(
                        kelas = kelas,
                        description = desc,
                        confidence = conf.toDouble(),
                        imgUrl = decodeStringUrl(imgUrl),
                    )
                    ScanResultScreen(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        scanResult = scanResult,
                        lat = lat,
                        long = long,
                        viewModel = scanViewModel
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
        OrnamanApp("")
    }
}