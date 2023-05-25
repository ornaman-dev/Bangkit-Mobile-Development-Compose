package com.example.ornamancompose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun IconCard(
    modifier : Modifier = Modifier,
    icon : Painter,
    title : String,
    onClickEvent : () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(10.dp)
            .wrapContentSize()
            .clickable {
                onClickEvent()
            }
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 10.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun IconCardPrev() {
    OrnamanComposeTheme {
        Column {
            IconCard(
                icon = painterResource(id = R.drawable.ic_folde_add), title = "Upload from gallery"
            )
            IconCard(
                icon = painterResource(id = R.drawable.ic_camera), title = "Take a picture"
            )
        }
    }
}