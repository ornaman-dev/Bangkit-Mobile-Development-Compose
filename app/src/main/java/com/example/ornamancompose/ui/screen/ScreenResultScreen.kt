package com.example.ornamancompose.ui.screen

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.Geometry
import com.example.ornamancompose.model.remote.Location
import com.example.ornamancompose.model.remote.PlantScanResponse
import com.example.ornamancompose.model.remote.ResultsItem
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
    viewModel: ScanViewModel,
    navController: NavController
) {
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
            text = scanResult.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.nearby_plant_store),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight(700)
            ),
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
                        .padding(top = 5.dp, bottom = 80.dp)
                        .fillMaxWidth(),
                    data = (nearbyStoreState as UiState.Success<List<ResultsItem>>).data
                )
            }
        }

//        ListRecommendationStore(
//            modifier = Modifier
//                .padding(top = 5.dp, bottom = 80.dp)
//                .fillMaxWidth(),
//            data = dummyNearbyLocationData
//        )

        //Todo(clear scan state after the back system pressed)
        BackHandler(true) {
            viewModel.resetProperties()
            navController.popBackStack()
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
        Log.i("ScreenResult-TAG", "$data")
        if(data.isEmpty()){
            item {
                Text(
                    text = stringResource(R.string.no_nearby_store_found),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }else{
            items(data){ item ->
                StoreCard(data = item)
            }
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
//fun ScanResultPreview() {
//    OrnamanComposeTheme {
//        ScanResultScreen(
//            scanResult = PlantScanResponse(
//                "Anggrek",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
//                ""
//            ),
//            lat = "",
//            long = "",
//            modifier = Modifier
//                .fillMaxSize()
//        )
//    }
//}

private val dummyNearbyLocationData = listOf(
    ResultsItem(
        rating = "4.0",
        photos = emptyList(),
        name = "Toko jaya baru",
        openingHours = null,
        geometry = Geometry(Location(0.0, 0.0)),
        vicinity = "Jl. Anggrek kenangan"
    )
)

