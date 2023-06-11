package com.example.ornamancompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun PlantBanner(
    modifier : Modifier = Modifier,
    painter : Painter = painterResource(R.drawable.plant_banner),
    text : String = stringResource(R.string.plant_saying)
) {
    val shape = RoundedCornerShape(3.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .clip(shape)
                .fillMaxWidth()
                .height(130.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.8F
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantBannerPreview() {
    OrnamanComposeTheme {
        PlantBanner(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}