package com.example.ornamancompose.ui.component

import com.example.ornamancompose.model.remote.PlantResponse
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.util.styleStringResource

@Composable
fun PlantCard(
    modifier: Modifier = Modifier,
    data : PlantResponse,
    onClick : (String) -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .border(width = 1.dp, MaterialTheme.colorScheme.primary, shape)
            .clip(shape)
            .clickable {
                onClick(data.id)
            }
    ) {
        AsyncImage(
            model = data.imgUrl,
            placeholder = painterResource(R.drawable.placeholder_image),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(130.dp)
                .clip(shape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .width(200.dp)
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = styleStringResource(stringResource(R.string.common_name_and_class_name, data.altName, data.name)),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight(700)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.desc,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PlantCardPreview() {
    OrnamanComposeTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            PlantCard(
                data = PlantResponse(
                    altName = "Agglonema",
                    desc = "Sample quick description Sample quick description Sample quick description Sample quick description",
                    imgUrl = "",
                    name = "Sample family name",
                    id = ""
                )
            ){}
        }
    }
}