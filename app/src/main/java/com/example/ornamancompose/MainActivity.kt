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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.ui.component.BottomNav
import com.example.ornamancompose.ui.navigation.Screen
import com.example.ornamancompose.ui.screen.ScanScreen

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
   val currentDestinationRoute = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomNav(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                selected = {route ->
                    currentDestinationRoute.value?.destination?.route == route
                },
                onItemClick = {route ->
                    navController.navigate(route)
                }
            )
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrnamanAppPreview() {
    OrnamanComposeTheme {
        OrnamanApp()
    }
}