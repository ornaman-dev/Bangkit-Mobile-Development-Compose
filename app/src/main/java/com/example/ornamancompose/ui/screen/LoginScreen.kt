package com.example.ornamancompose.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.InputText
import com.example.ornamancompose.ui.component.PrimaryButton
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory

@Composable
fun LoginScreen(
    modifier : Modifier = Modifier,
    viewModel : AuthViewModel,
    onSignUpClick : () -> Unit = {},
    onSuccessLogin : () -> Unit = {}
) {

    var username by remember {
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    var isLoading by remember{
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val loginState by viewModel.loginStateFlow.collectAsState()

    LaunchedEffect(loginState){
        Log.i("LOGIN-TAG", "$loginState")
        when(loginState){
            is UiState.Loading -> isLoading = true
            is UiState.Error -> {
                isLoading = false
                showToast(context, (loginState as UiState.Error).message)
            }
            is UiState.Exception -> {
                isLoading = false
                showToast(context, (loginState as UiState.Exception).message)
            }
            is UiState.Success -> {
                if((loginState as UiState.Success).data.accessToken.isNotEmpty()){
                    onSuccessLogin()
                }
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = stringResource(R.string.login_greeting),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(R.string.login_greeting_2),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.login_greeting_3),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .fillMaxWidth()
            )
            InputText(
                placeholder = stringResource(R.string.email_placeholder),
                onValueChanged = { newValue ->
                    username = newValue
                },
                text = username
            )
            InputText(
                placeholder = stringResource(R.string.password_placeholder),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(bottom = 50.dp),
                onValueChanged = {newValue ->
                    password = newValue
                },
                text = password,
                isPasswordType = true
            )
            if(isLoading){
                ProgressBar()
            }else{
                PrimaryButton(
                    text = stringResource(R.string.login),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if(username.isNotEmpty() && password.isNotEmpty()){
                            viewModel.login(username.trim(), password.trim())
                        }else{
                            showToast(context, context.getString(R.string.username_or_password_empty))
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dont_have_an_account),
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(
                    onClick = onSignUpClick
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    OrnamanComposeTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel<AuthViewModel>(factory = ViewModelFactory.getInstance())
        )
    }
}

