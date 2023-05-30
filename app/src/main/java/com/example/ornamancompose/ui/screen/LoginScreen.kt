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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.ui.component.InputText
import com.example.ornamancompose.ui.component.PrimaryButton

@Composable
fun LoginScreen(
    modifier : Modifier = Modifier,
    onLoginClick : () -> Unit = {},
    onSignUpClick : () -> Unit = {}
) {
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
                placeholder = stringResource(R.string.username_placeholder),
                errorRule = {text ->
                    text.length < 8 && text.isNotEmpty()
                },
                errorMessage = stringResource(R.string.username_error_message)
            )
            InputText(
                placeholder = stringResource(R.string.password_placeholder),
                errorRule = {text ->
                    text.length < 8 && text.isNotEmpty()
                },
                visualTransformation = PasswordVisualTransformation(),
                errorMessage = stringResource(R.string.password_error_message),
                modifier = Modifier
                    .padding(bottom = 50.dp)
            )
            PrimaryButton(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onLoginClick
            )
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
        LoginScreen(modifier = Modifier.fillMaxSize())
    }
}

