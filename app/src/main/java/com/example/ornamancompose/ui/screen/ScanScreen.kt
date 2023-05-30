package com.example.ornamancompose.ui.screen

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ornamancompose.R
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.IconCard
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.createCustomTempFile
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.util.uriToFile
import com.example.ornamancompose.viewmodel.ScanViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory
import java.io.File

private const val SCANSCREENTAG = "scan_screen_tag"

private lateinit var currentFilePath : String
@Composable
fun ScanScreen(
    modifier : Modifier = Modifier
) {

    val context = LocalContext.current
    var uploadedFile by remember{
        mutableStateOf<File?>(null)
    }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri ->
        if(uri != null){
            uploadedFile = uriToFile(uri, context)
        }
    }

    val launcherIntentCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == RESULT_OK){
            uploadedFile = File(currentFilePath)
            Log.i(SCANSCREENTAG, "File : $uploadedFile")
        }
    }

    val viewModel : ScanViewModel = viewModel(
        factory = ViewModelFactory.getInstance()
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        if(uploadedFile == null){
            Scan(
                onClickUploadGallery = {
                    launcherGallery.launch("image/jpeg image/png")
                },
                onClickTakePicture = {
                    takePhoto(context){intent ->
                        launcherIntentCamera.launch(intent)
                    }
                }
            )
        }else{
            // Request goes here
            viewModel.scanPlant(uploadedFile!!).collectAsState(initial = UiState.Loading).value.let{ uiState ->
                when(uiState){
                    is UiState.Loading -> {
                        ProgressBar()
                    }
                    is UiState.Exception -> {
                        showToast(context, uiState.message)
                    }
                    is UiState.Error -> {
                        val message = "${uiState.code} : ${uiState.message}"
                        showToast(context, message)
                    }
                    is UiState.Success -> {
                        ScanResultScreen(
                            modifier = Modifier
                                .fillMaxSize(),
                            scanResult = uiState.data
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Scan(
    modifier: Modifier = Modifier,
    onClickUploadGallery : () -> Unit,
    onClickTakePicture : () -> Unit
) {
    Box(
        modifier = modifier
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
                title = stringResource(R.string.action_upload_from_gallery),
                onClickEvent = onClickUploadGallery
            )
            IconCard(
                icon = painterResource(R.drawable.ic_camera),
                title = stringResource(R.string.action_take_a_picture),
                onClickEvent = onClickTakePicture
            )
        }

    }
}

private fun takePhoto(context : Context, launchIntentCamera : (Intent) -> Unit){
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.resolveActivity(context.packageManager)

    createCustomTempFile(context.applicationContext).also {
        val photoURI : Uri = FileProvider.getUriForFile(
            context,
            "com.example.ornamancompose",
            it
        )
        currentFilePath = it.absolutePath
        Log.i(SCANSCREENTAG, currentFilePath)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        launchIntentCamera(intent)
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

