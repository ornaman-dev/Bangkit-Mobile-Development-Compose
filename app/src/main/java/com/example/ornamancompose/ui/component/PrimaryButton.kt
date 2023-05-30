package com.example.ornamancompose.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme

@Composable
fun PrimaryButton(
    modifier : Modifier = Modifier,
    text : String,
    onClick : () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview(){
    OrnamanComposeTheme {
        PrimaryButton(
            text = "Login",
            modifier = Modifier.fillMaxWidth()
        ){

        }
    }
}