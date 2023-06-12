package com.example.ornamancompose.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputText(
    modifier : Modifier = Modifier,
    placeholder : String,
    errorRule : (String) -> Boolean = {false},
    visualTransformation : VisualTransformation = VisualTransformation.None,
    errorMessage : String = "",
    onValueChanged : (String) -> Unit,
    text : String,
    isPasswordType : Boolean = false
) {

    var visualTransformationState by remember{
        mutableStateOf(visualTransformation)
    }

    TextField(
        value = text,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp)),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(0.dp)
            )
        },
        singleLine = true,
        isError = errorRule(text),
        visualTransformation = visualTransformationState,
        supportingText = {
            if(errorRule(text)){
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(5.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        trailingIcon = {
            if(isPasswordType){
                IconButton(
                    onClick = {
                        visualTransformationState = if(visualTransformationState == VisualTransformation.None) PasswordVisualTransformation() else VisualTransformation.None
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (visualTransformationState == VisualTransformation.None) R.drawable.eye else R.drawable.eye_crossed),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextPreview() {
    OrnamanComposeTheme {
        InputText(
            placeholder = "Username",
            onValueChanged = {_ -> },
            text = "",
            isPasswordType = true
        )
    }
}