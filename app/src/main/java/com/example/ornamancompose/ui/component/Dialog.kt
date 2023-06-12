package com.example.ornamancompose.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.compose.OrnamanComposeTheme

@Composable
fun PermissionDialog(
    title : String,
    negativeText : String,
    positiveText : String,
    negativeAction : () -> Unit,
    positiveAction : () -> Unit,
    dismissAction : () -> Unit
) {
    Dialog(onDismissRequest = { dismissAction() }) {
        OutlinedCard(
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 20.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextButton(onClick = { negativeAction() }) {
                    Text(
                        text = negativeText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(700)
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TextButton(onClick = { positiveAction() }) {
                    Text(
                        text = positiveText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(700)
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionDialogPreview() {
    OrnamanComposeTheme {
        PermissionDialog(
            title = "Location is required to recommend nearby store",
            negativeText = "Cancel",
            positiveText = "Turn on",
            negativeAction = {  },
            positiveAction = { },
            dismissAction = {}
        )
    }
}