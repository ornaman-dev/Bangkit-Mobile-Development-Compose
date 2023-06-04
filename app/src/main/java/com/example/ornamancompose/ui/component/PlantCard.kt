package com.example.ornamancompose.ui.component

import DummyPlantResponse
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun PlantCard(
    modifier: Modifier = Modifier,
    data : DummyPlantResponse
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        AsyncImage(
            model = data.imgUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            placeholder = painterResource(R.drawable.placeholder_image),
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = data.quickDescription,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = data.profileImgUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.sample_profile)
            )
            Text(
                text = data.publisher,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_time),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .size(20.dp)
                    .clip(CircleShape)
            )
            Text(
                text = data.publishedAt,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PlantCardPreview() {
    OrnamanComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PlantCard(
                modifier = Modifier
                    .fillMaxWidth(),
                data = DummyPlantResponse(
                    title = "Sample title",
                    quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
                    publishedAt = "4 hours ago",
                    publisher = "Joko"
                )
            )
        }
    }
}