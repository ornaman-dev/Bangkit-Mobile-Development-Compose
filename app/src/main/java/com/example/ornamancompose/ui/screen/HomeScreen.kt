package com.example.ornamancompose.ui.screen

import DummyPlantResponse
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.ui.component.PlantCard

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        ListPlantCard(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun ListPlantCard(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
    ){
        items(dummyData){ item ->
            PlantCard(
                data = item
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    OrnamanComposeTheme {
//        HomeScreen(
//            modifier = Modifier
//                .fillMaxSize()
//        )
//    }
//}

private val dummyData = listOf(
    DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    ),
    DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    ),DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    ),DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    ),DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    ),DummyPlantResponse(
        title = "Sample title",
        quickDescription = "Sample quick description Sample quick description Sample quick description Sample quick description",
        publishedAt = "4h ago",
        publisher = "Joko"
    )
)