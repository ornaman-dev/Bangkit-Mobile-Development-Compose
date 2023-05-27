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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
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
import com.example.ornamancompose.R
import com.example.ornamancompose.ui.component.IconCard
import com.example.ornamancompose.util.createCustomTempFile
import java.io.File
import kotlin.contracts.contract

private const val SCANSCREENTAG = "scan_screen_tag"

private lateinit var currentFilePath : String
private lateinit var uploadedFile : File
@Composable
fun ScanScreen(
    modifier : Modifier = Modifier
) {

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember{
        mutableStateOf<Bitmap?>(null)
    }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){result ->
        imageUri = result
    }

    val launcherIntentCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == RESULT_OK){
            uploadedFile = File(currentFilePath)
            Log.i(SCANSCREENTAG, "File : $uploadedFile")
        }
    }

    val context = LocalContext.current

    imageUri?.let{
        bitmap = if(Build.VERSION.SDK_INT < 28){
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        }else{
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }

    Log.i(SCANSCREENTAG, "$bitmap")

    Scan(
        modifier = modifier,
        onClickUploadGallery = {
            launcherGallery.launch("image/*")
        },
        onClickTakePicture = {
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
                launcherIntentCamera.launch(intent)
            }
        }
    )
}

@Composable
fun Scan(
    modifier: Modifier = Modifier,
    onClickUploadGallery : () -> Unit,
    onClickTakePicture : () -> Unit
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
}

private fun takePhoto(context : Context, launchIntentCamera : (Intent) -> Unit){

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

