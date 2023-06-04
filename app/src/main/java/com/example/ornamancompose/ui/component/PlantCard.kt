package com.example.ornamancompose.ui.component

import DummyPlantResponse
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
        Image(
            painter = painterResource(R.drawable.sample_plant),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = data.quickDescription,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = data.profileImgUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp),
                placeholder = painterResource(R.drawable.sample_profile),
                contentScale = ContentScale.Crop
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
                    .weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.ic_time),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .size(18.dp)
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
                .padding(10.dp)
                .wrapContentHeight()
        ) {
            PlantCard(
                modifier = Modifier
                    .fillMaxWidth(),
                data = DummyPlantResponse(
                    title = "Sample title",
                    quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
                    publishedAt = "4h ago",
                    publisher = "Joko"
                )
            )
        }
    }
}