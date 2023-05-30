package com.example.ornamancompose.ui.screen

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.ui.component.PrimaryButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.InputText
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.AuthViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory

@Composable
fun RegisterScreen(
    modifier : Modifier = Modifier,
    viewModel: AuthViewModel,
    onSignInClick : () -> Unit = {},
    onSuccessRegister : () -> Unit = {}
) {

    var username by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    var name by remember{
        mutableStateOf("")
    }
    var isLoading by remember{
        mutableStateOf(false)
    }
    var requestCounter by remember{
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val registerState by viewModel.registerStateFlow.collectAsState()

    LaunchedEffect(requestCounter){
        when(registerState){
            is UiState.Loading -> isLoading = true
            is UiState.Error -> {
                isLoading = false
                showToast(context, "${(registerState as UiState.Error).code} \\t message : ${(registerState as UiState.Error).message}\"")
            }
            is UiState.Exception -> {
                isLoading = false
                showToast(context, (registerState as UiState.Exception).message)
            }
            is UiState.Success -> {
                if((registerState as UiState.Success).data){
                    onSuccessRegister()
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
                text = stringResource(R.string.join),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(R.string.us),
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
                placeholder = stringResource(R.string.name_placeholder),
                onValueChanged = {newValue ->
                    name = newValue
                },
                text = name
            )
            InputText(
                placeholder = stringResource(R.string.username_placeholder),
                errorRule = {text ->
                    text.length < 8 && text.isNotEmpty()
                },
                errorMessage = stringResource(R.string.username_error_message),
                onValueChanged = {newValue ->
                    username = newValue
                },
                text = username
            )
            InputText(
                placeholder = stringResource(R.string.password_placeholder),
                errorRule = {text ->
                    text.length < 8 && text.isNotEmpty()
                },
                visualTransformation = PasswordVisualTransformation(),
                errorMessage = stringResource(R.string.password_error_message),
                modifier = Modifier
                    .padding(bottom = 50.dp),
                onValueChanged = {newValue ->
                    password = newValue
                },
                text = password
            )
            if(isLoading){
                ProgressBar()
            }else{
                PrimaryButton(
                    text = stringResource(R.string.register),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.register(name, username, password)
                        requestCounter++
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
                    text = stringResource(R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(
                    onClick = onSignInClick
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreen() {
    OrnamanComposeTheme {
        RegisterScreen(
            modifier = Modifier
                .fillMaxSize(),
            viewModel = viewModel<AuthViewModel>(factory = ViewModelFactory.getInstance())
        )
    }
}