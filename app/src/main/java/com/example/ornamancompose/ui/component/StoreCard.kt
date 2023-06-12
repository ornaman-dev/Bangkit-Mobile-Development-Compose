package com.example.ornamancompose.ui.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ornamancompose.R
import com.example.ornamancompose.model.remote.ResultsItem
import com.example.ornamancompose.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreCard(
    modifier : Modifier = Modifier,
    data : ResultsItem
) {

    val context = LocalContext.current
    val isStoreOpen = data.openingHours?.openNow ?: false
    OutlinedCard(
        modifier = modifier
            .width(200.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp),
        onClick = {
            intentToGoogleMap(context, data)
        }
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
                text = data.name,
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
                text = data.vicinity,
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
                    text = if(isStoreOpen)"Buka" else "Tutup",
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
                    text = data.rating,
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

private fun intentToGoogleMap(context : Context, data : ResultsItem){
    val location = data.geometry.location
    val gmmIntentUri = Uri.parse("geo:${location.lat},${location.lng}?q=${location.lat},${location.lng}(${data.name})")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.resolveActivity(context.packageManager)?.let{
        context.startActivity(mapIntent)
    }
}
