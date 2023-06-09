package com.example.ornamancompose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.ornamancompose.model.datastore.User
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val viewModel : AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this@SplashActivity)
    }
    private lateinit var userSession : User

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition{
                true
            }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            userSession = viewModel.getUserSession().first()
            delay(1500)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.putExtra("token", userSession.token)
            startActivity(intent)
            finish()
        }
    }
}