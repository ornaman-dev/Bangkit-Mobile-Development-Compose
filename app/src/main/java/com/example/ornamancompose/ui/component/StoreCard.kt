package com.example.ornamancompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ornamancompose.R
import com.example.ornamancompose.ui.theme.Poppins

@Composable
fun StoreCard(
    modifier : Modifier = Modifier,
    name : String,
    address : String,
    isOpen : Boolean,
    rating : String
) {
    OutlinedCard(
        modifier = modifier
            .width(200.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.plant_store),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ){
            Text(
                text = name,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    letterSpacing = 0.12.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = address,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 9.sp,
                    lineHeight = 19.sp,
                    letterSpacing = 0.12.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Text(
                    text = if(isOpen)"Buka" else "Tutup",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight(700),
                        fontSize = 9.sp,
                        lineHeight = 19.sp,
                        letterSpacing = 0.12.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                )
                Text(
                    text = "$rating",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 9.sp,
                        lineHeight = 19.sp,
                        letterSpacing = 0.12.sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun StoreCardPreview() {
    Scaffold {paddingValues ->
        StoreCard(
            modifier = Modifier
                .padding(paddingValues),
            name = "Toko pupuk jaya baru",
            address = "Jl. Jati kenangan No. 25",
            isOpen = true,
            rating = "4.0"
        )
    }
}