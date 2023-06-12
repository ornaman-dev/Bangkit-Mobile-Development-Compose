package com.example.ornamancompose.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

data class Treatment(
    @DrawableRes val icon : Int,
    val content : String,
    val type : String
)

@Composable
fun TreatmentCard(
    modifier : Modifier = Modifier,
    data : Treatment
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, shape)
            .clip(shape)
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(data.icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(30.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = data.type,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .alpha(0.7f)
            )
            Text(
                text = data.content,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TreatmentCardPreview() {
    OrnamanComposeTheme {
        TreatmentCard(
            data = Treatment(
                type = "Light",
                content = "Anthurium lebih suka cahaya terang yang tidak langsung. Sinar matahari langsung dapat membakar daunnya. Semakin banyak cahaya yang diterima tanaman, semakin banyak bunga yang akan dihasilkan.",
                icon = R.drawable.ic_sun
            )
        )
    }
}