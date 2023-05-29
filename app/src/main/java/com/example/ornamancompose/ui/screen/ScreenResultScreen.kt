package com.example.ornamancompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.PlantScanResponse

@Composable
fun ScanResultScreen(
    modifier : Modifier = Modifier,
    scanResult : PlantScanResponse
) {
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        CardImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            //imgUrl = scanResult.imgUrl
        )
        Text(
            text = stringResource(R.string.plant_name, scanResult.kelas),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.confidence_prediction, scanResult.confidence),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = scanResult.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardImage(
    modifier: Modifier = Modifier,
    imgUrl : String? = null
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp)
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.plant)
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun CardImagePrev() {
//    OrnamanComposeTheme {
//        ScanResultScreen(
//            scanResult = PlantScanResponse("","","")
//        )
//    }
//}

