package com.example.ornamancompose.ui.navigation

import com.example.ornamancompose.model.remote.PlantScanResponse
import com.example.ornamancompose.util.encodeStringUrl


sealed class Screen(val route : String) {
    object Home : Screen("Home")
    object Scan : Screen("Scan")
    object Profile : Screen("Profile")
    object Login : Screen("Login")
    object Register : Screen("Register")
    object ScanResult : Screen("Scan_Result/{kelas}/{desc}/{imgUrl}/{lat}/{long}"){
        fun createRoute(scanResponse: PlantScanResponse, lat : String, long : String) : String{
            val encodedImgUrl = encodeStringUrl(scanResponse.imgUrl)
            return "Scan_Result/${scanResponse.kelas}/${scanResponse.description}/$encodedImgUrl/$lat/$long"
        }
    }
    object DetailPlant : Screen("Detail/{plantId}"){
        fun createRoute(plantId : String) : String = "Detail/$plantId"
    }
}