package com.example.ornamancompose.ui.screen

import PlantResponse
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ornamancompose.R
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.PlantBanner
import com.example.ornamancompose.ui.component.PlantCard
import com.example.ornamancompose.ui.component.PlantRandomFact
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel : HomeViewModel
) {

    PlantColumn(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp)
            .fillMaxSize(),
        viewModel = viewModel
    )

}

@Composable
fun PlantColumn(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val allPlantsState by viewModel.allPlants.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit){
        viewModel.getAllPlants()
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.ornaman_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(175.dp)
                    .height(35.dp)
            )
        }
            PlantBanner(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.did_you_know),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
            )
            PlantRandomFact(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.plant_guide),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.ic_arrow_right_2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                when(val state = allPlantsState){
                    is UiState.Loading -> {
                        ProgressBar()
                    }
                    is UiState.Exception -> {
                        showToast(context, state.message)
                    }
                    is UiState.Error -> {
                        showToast(context, state.message)
                    }
                    is UiState.Success -> {
                        ListPlantCard(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            data = state.data
                        )
                    }
                }
            }
    }

}

@Composable
fun ListPlantCard(
    modifier: Modifier = Modifier,
    data : List<PlantResponse>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
    ){
        items(data){ item ->
            PlantCard(data = item)
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    OrnamanComposeTheme {
//        PlantColumn(
//            modifier = Modifier
//                .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp)
//                .fillMaxSize()
//        )
//    }
//}

private val dummyData = listOf(
    PlantResponse(
        altName = "Agglonema",
        desc = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        name = "Sample family name",
        id = ""
    ),

    PlantResponse(
        altName = "Agglonema",
        desc = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        name = "Sample family name",
        id = ""
    ),
    PlantResponse(
        altName = "Agglonema",
        desc = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        name = "Sample family name",
        id = ""
    )
)