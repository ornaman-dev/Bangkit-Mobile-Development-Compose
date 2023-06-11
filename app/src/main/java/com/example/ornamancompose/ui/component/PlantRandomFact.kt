package com.example.ornamancompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@Composable
fun PlantRandomFact(
    modifier : Modifier = Modifier,
    fact : String = stringResource(R.string.random_plant_fact)
) {
    val shape = RoundedCornerShape(8.dp)
    OutlinedCard(
        modifier = modifier,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 25.dp)
                .clip(shape)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
               painter = painterResource(R.drawable.bulb),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 14.dp)
                    .size(40.dp)
            )
            Text(
                text = fact,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantRandomFactPreview() {
    OrnamanComposeTheme {
        PlantRandomFact(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}