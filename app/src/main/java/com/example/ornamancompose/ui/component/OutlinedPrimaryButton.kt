package com.example.ornamancompose.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun OutlinedPrimaryButton(
    modifier : Modifier = Modifier,
    icon : Painter,
    clicked : Boolean = false,
    onClicked : () -> Unit = {}
) {
    val shape = RoundedCornerShape(8.dp)
    val color = if(clicked) Color.White else MaterialTheme.colorScheme.primary
    val backgroundColor = if(clicked) MaterialTheme.colorScheme.primary else Color.White
    val border = BorderStroke(1.dp, color)

    OutlinedButton(
        onClick = onClicked,
        modifier = Modifier
            .padding(horizontal = 7.dp, vertical = 10.dp),
        shape = shape,
        border = border,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .padding(end = 10.dp)
        )
        Text(
            text = "Like",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight(600),
                fontSize = 15.sp
            ),
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedPrimaryButtonPreview() {
    OrnamanComposeTheme {
        OutlinedPrimaryButton(
            icon = painterResource(R.drawable.ic_favorite)
        )
    }
}