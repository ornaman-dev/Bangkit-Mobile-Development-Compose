package com.example.ornamancompose.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme

@Composable
fun ProgressBar(
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProgressBarPreview() {
    OrnamanComposeTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            ProgressBar(modifier = Modifier
                .size(50.dp))
        }
    }
}