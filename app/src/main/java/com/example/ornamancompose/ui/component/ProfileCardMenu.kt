package com.example.ornamancompose.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun ProfileCardMenu(
    modifier : Modifier = Modifier,
    title : String,
    color : Color = MaterialTheme.colorScheme.primary,
    icon : Painter = painterResource(R.drawable.ic_arrow_right)
) {
        Row(
            modifier = modifier
                .border(1.dp, color)
                .clip(RoundedCornerShape(8.dp))
                .padding(15.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                icon,
                contentDescription = null
            )
        }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardMenuPreview() {
    OrnamanComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            ProfileCardMenu(
                title = "Account"
            )
        }
    }
}