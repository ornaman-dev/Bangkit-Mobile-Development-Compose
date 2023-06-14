package com.example.ornamancompose.ui.screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.LoginResponse
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.ProfileCardMenu
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    modifier : Modifier = Modifier,
    viewModel : AuthViewModel,
    onLogout : () -> Unit
) {

    val userSessionState by viewModel.userSessionStateFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.getUserSession()
    }

    when(val state = userSessionState){
        is UiState.Loading -> {
            ProgressBar(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        is UiState.Exception -> {
            showToast(context, state.message)
        }
        is UiState.Error -> {
            showToast(context, state.message)
        }
        is UiState.Success -> {
            ProfileApp(
                modifier = modifier,
                loginResponse = state.data,
                onLogout = onLogout
            )
        }
    }


}

@Composable
fun ProfileApp(
    modifier: Modifier = Modifier,
    loginResponse: LoginResponse,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.sample_profile),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Todo(Replaced the name and email value to the ones saved on data store)
                Text(
                    text = loginResponse.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = loginResponse.email,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Text(
            text = stringResource(R.string.account),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
        )
        ProfileCardMenu(
            title = stringResource(R.string.language),
            onClick = {
                context.startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        )
        ProfileCardMenu(
            title = stringResource(R.string.logout),
            color = MaterialTheme.colorScheme.error,
            icon = painterResource(R.drawable.ic_exit),
            onClick = onLogout
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    OrnamanComposeTheme {
        ProfileApp(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            loginResponse = LoginResponse(
                id = "",
                name = "Joko Hartono",
                email = "Joko@gmail.com",
                accessToken = ""
            ),
            onLogout = {}
        )
    }
}

