package com.example.ornamancompose.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.PlantRecommendationResponse

@Composable
fun PlantRecommendCard(
    modifier : Modifier = Modifier,
    data : PlantRecommendationResponse
) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, shape)
            .clip(shape)
            .padding(12.dp)
            .width(220.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = data.imgUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.placeholder_image),
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.altName,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight(600)
            ),
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = data.desc,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantRecommendCardPreview() {
    OrnamanComposeTheme {
        PlantRecommendCard(
            data = PlantRecommendationResponse(
                "",
                "Anggrek",
                "",
                "Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek Anggrek"
            )
        )
    }
}