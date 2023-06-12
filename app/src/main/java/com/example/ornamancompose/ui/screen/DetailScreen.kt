package com.example.ornamancompose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.PlantDetailResponse
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.ui.component.Treatment
import com.example.ornamancompose.ui.component.TreatmentCard
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.util.styleStringResource
import com.example.ornamancompose.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    modifier : Modifier = Modifier,
    plantId : String,
    viewModel : HomeViewModel
) {
    val context = LocalContext.current
    val detailPlantState by viewModel.detailPlant.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getDetailPlant(plantId)
    }

    when(val state = detailPlantState){
        is UiState.Loading -> {
            ProgressBar(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        is UiState.Error -> {
            showToast(context, state.message)
        }
        is UiState.Exception -> {
            showToast(context, state.message)
        }
        is UiState.Success -> {
            DetailPlant(
                data = state.data
            )
        }
    }
}

@Composable
fun DetailPlant(
    modifier: Modifier = Modifier,
    data : PlantDetailResponse
) {

    val treatments = convertTreatmentsToList(data)
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 60.dp)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = data.imgUrl,
            placeholder = painterResource(R.drawable.placeholder_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = styleStringResource(stringResource(R.string.common_name_and_class_name, data.altName, data.name)),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = data.desc,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Treatment",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(vertical = 15.dp)
        )
        treatments.forEach{treatment ->
            TreatmentCard(
                data = treatment,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
        }
    }
}

private fun convertTreatmentsToList(data : PlantDetailResponse) : List<Treatment>{
    val icons = listOf(
        R.drawable.ic_sun,
        R.drawable.ic_water,
        R.drawable.ic_humidity,
        R.drawable.ic_temperature,
        R.drawable.ic_food,
        R.drawable.ic_posion,
        R.drawable.ic_hand_heart,
        R.drawable.ic_light_bulb
    )
    val types = listOf(
        "Light", "Water", "Humidity", "Temperature", "Food", "Toxicity", "Cares", "Fact"
    )
    val contents = listOf(
        data.light, data.water, data.humidity, data.temperature, data.food, data.toxicity, data.cares, data.fact
    )

    val treatments = mutableListOf<Treatment>()

    val size = icons.size
    for(i in 0 until size){
        val treatment = Treatment(
            icon = icons[i],
            type = types[i],
            content = contents[i]
        )
        treatments.add(treatment)
    }
    return treatments
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPlantPreview() {
    OrnamanComposeTheme {
        DetailPlant(
            data = PlantDetailResponse(
                "asda",
                "Aglonema",
                "Gelombang Cinta",
                "",
                "Pucuk Merah (Syzygium Myrtifolium) adalah spesies tumbuhan yang dikenal sebagai tanaman hias yang berasal dari genus Syzygium.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya.",
                "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya."
            )
        )
    }
}