package com.example.ornamancompose.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputText(
    modifier : Modifier = Modifier,
    placeholder : String
) {
    var text by remember{
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        trailingIcon = {
            if(text.isNotEmpty()){
                IconButton(
                    onClick = { text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
            .padding(15.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(5.dp)),
        placeholder = {
            Text(text = placeholder)
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun InputTextPreview() {
//    OrnamanComposeTheme {
//        InputText(
//            placeholder = "Username"
//        )
//    }
//}