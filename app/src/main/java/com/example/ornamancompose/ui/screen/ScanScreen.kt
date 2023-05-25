package com.example.ornamancompose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.ui.component.IconCard
import com.example.ornamancompose.R

@Composable
fun ScanScreen(
    modifier : Modifier = Modifier
) {
    Scan(modifier = modifier)
}

@Composable
fun Scan(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .wrapContentSize(),
            ) {
                IconCard(
                    icon = painterResource(R.drawable.ic_folde_add),
                    title = stringResource(R.string.action_upload_from_gallery)
                )
                IconCard(
                    icon = painterResource(R.drawable.ic_camera),
                    title = stringResource(R.string.action_take_a_picture)
                )
            }

        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ScanPreview() {
//    OrnamanComposeTheme {
//        Scan(
//            modifier = Modifier
//                .fillMaxSize()
//        )
//    }
//}

