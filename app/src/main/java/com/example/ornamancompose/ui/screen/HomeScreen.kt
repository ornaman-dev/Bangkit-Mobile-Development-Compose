package com.example.ornamancompose.ui.screen

import DummyPlantResponse
import PlantResponse
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.PlantCard
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.viewmodel.HomeViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier
) {

    val viewModel : HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance()
    )

    val allPlantsState by viewModel.allPlants.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.getAllPlants()
    }

    Box(
        modifier = modifier,
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
                        .fillMaxSize(),
                    data = state.data
                )
            }
        }
    }
}

@Composable
fun ListPlantCard(
    modifier: Modifier = Modifier,
    data : List<PlantResponse>
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
    ){
        items(data){ item ->
            PlantCard(data = item)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    OrnamanComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            ListPlantCard(
                modifier = Modifier
                    .fillMaxSize(),
                data = dummyData
            )
        }
    }
}

private val dummyData = listOf(
    PlantResponse(
        className = "Agglonema",
        description = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        familyName = "Sample family name",
        location = "",
        taxonomicDataUrl = "",
        datePosted = "",
        commonName = "Anggrek"
    ),
    PlantResponse(
        className = "Agglonema",
        description = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        familyName = "Sample family name",
        location = "",
        taxonomicDataUrl = "",
        datePosted = "",
        commonName = "Anggrek"
    ),PlantResponse(
        className = "Agglonema",
        description = "Sample quick description Sample quick description Sample quick description Sample quick description",
        imgUrl = "",
        familyName = "Sample family name",
        location = "",
        taxonomicDataUrl = "",
        datePosted = "",
        commonName = "Anggrek"
    )
)