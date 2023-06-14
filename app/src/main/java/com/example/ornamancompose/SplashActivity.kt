package com.example.ornamancompose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.ornamancompose.model.remote.LoginResponse
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val viewModel : AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this@SplashActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition{
                true
            }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getUserSession()
            delay(1500)
            val userSession = viewModel.userSessionStateFlow.first() as UiState.Success
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.putExtra("token", userSession.data.accessToken)
            startActivity(intent)
            finish()
        }
    }
}