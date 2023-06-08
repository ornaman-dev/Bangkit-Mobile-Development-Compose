package com.example.ornamancompose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.InputText
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.isEmailValid
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
    var email by remember{
        mutableStateOf("")
    }
    var isLoading by remember{
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val registerState by viewModel.registerStateFlow.collectAsState()

    LaunchedEffect(registerState){
        when(val state = registerState){
            is UiState.Loading -> isLoading = true
            is UiState.Error -> {
                isLoading = false
                showToast(context, "${state.code} \\t message : ${state.message}\"")
            }
            is UiState.Exception -> {
                isLoading = false
                showToast(context, state.message)
            }
            is UiState.Success -> {
                if(state.data.username.isNotEmpty()){
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
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState()),
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
                placeholder = stringResource(R.string.username_placeholder),
                onValueChanged = {newValue ->
                    username = newValue
                },
                text = username
            )
            InputText(
                placeholder = stringResource(R.string.email_placeholder),
                errorRule = {text ->
                    !isEmailValid(text) && email.isNotEmpty()
                },
                errorMessage = stringResource(R.string.email_error_message),
                onValueChanged = {newValue ->
                    email = newValue
                },
                text = email
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
                text = password,
                isPasswordType = true
            )
            if(isLoading){
                ProgressBar()
            }else{
                PrimaryButton(
                    text = stringResource(R.string.register),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()){
                            viewModel.register(username.trim(), email.trim(), password.trim())
                        }else{
                            showToast(context, context.getString(R.string.field_empty))
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