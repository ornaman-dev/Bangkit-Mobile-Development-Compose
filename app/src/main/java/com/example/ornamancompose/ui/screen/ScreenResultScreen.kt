package com.example.ornamancompose.ui.screen

import Geometry
import Location
import OpeningHours
import PhotosItem
import PlantScanResponse
import ResultsItem
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.ui.component.StoreCard
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.ScanViewModel

@Composable
fun ScanResultScreen(
    modifier : Modifier = Modifier,
    scanResult : PlantScanResponse,
    lat : String,
    long : String,
    viewModel: ScanViewModel
) {

//    if(lat.isNotEmpty()){
//        Log.i("ScanResult-TAG", "$scanResult")
//        Log.i("ScanResult-TAG", lat)
//        Log.i("ScanResult-TAG", long)
//        return
//    }

    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()
    val nearbyStoreState by viewModel.searchNearbyStoreState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(verticalScrollState)
    ) {
        CardImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            imgUrl = scanResult.imgUrl
        )
        Text(
            text = stringResource(R.string.plant_name, scanResult.kelas),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.confidence_prediction, scanResult.confidence),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = scanResult.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.nearby_plant_store),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
        )
        LaunchedEffect(Unit){
            viewModel.searchNearbyStore(lat, long)
        }

        when(nearbyStoreState){
            is UiState.Loading -> {
                ProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            is UiState.Exception -> {
                showToast(context, (nearbyStoreState as UiState.Exception).message)
            }
            is UiState.Error -> {
                showToast(context, "code : ${(nearbyStoreState as UiState.Error).code} message : ${(nearbyStoreState as UiState.Error).message}")
            }
            is UiState.Success -> {
                Log.i("ScanResult-TAG", "${(nearbyStoreState as UiState.Success<List<ResultsItem>>).data}")
                ListRecommendationStore(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    data = (nearbyStoreState as UiState.Success<List<ResultsItem>>).data
                )
            }
        }
    }
}

@Composable
fun ListRecommendationStore(
    modifier : Modifier = Modifier,
    data : List<ResultsItem>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ){
        items(data){ item ->
            StoreCard(data = item)
        }
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
            placeholder = painterResource(R.drawable.placeholder_image),
            error = painterResource(R.drawable.placeholder_image)
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun CardImagePrev() {
//    OrnamanComposeTheme {
//        ScanResultScreen(
//            scanResult = PlantScanResponse("Anggrek","Lorem ipsum","90%", ""),
//            lat = "",
//            long = "",
//            data = dummyResultsItem,
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxSize()
//        )
//    }
//}

val dummyResultsItem = listOf(
    ResultsItem(
        rating = "4.0",
        photos = listOf(PhotosItem("")),
        name = "Toko jaya baru",
        vicinity = "Jl. Jati Kenangan No. 20",
        geometry = Geometry(Location(2.0, 3.0)),
        openingHours = OpeningHours(true)
    ),
    ResultsItem(
        rating = "4.0",
        photos = listOf(PhotosItem("")),
        name = "Toko jaya baru",
        vicinity = "Jl. Jati Kenangan No. 20",
        geometry = Geometry(Location(2.0, 3.0)),
        openingHours = OpeningHours(true)
    ),
    ResultsItem(
        rating = "4.0",
        photos = listOf(PhotosItem("")),
        name = "Toko jaya baru",
        vicinity = "Jl. Jati Kenangan No. 20",
        geometry = Geometry(Location(2.0, 3.0)),
        openingHours = OpeningHours(true)
    ),
    ResultsItem(
        rating = "4.0",
        photos = listOf(PhotosItem("")),
        name = "Toko jaya baru",
        vicinity = "Jl. Jati Kenangan No. 20",
        geometry = Geometry(Location(2.0, 3.0)),
        openingHours = OpeningHours(true)
    )
)

