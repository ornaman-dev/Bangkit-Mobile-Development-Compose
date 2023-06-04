package com.example.ornamancompose.ui.screen

import PlantScanResponse
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.OrnamanComposeTheme
import com.example.ornamancompose.R
import com.example.ornamancompose.repository.UiState
import com.example.ornamancompose.ui.component.IconCard
import com.example.ornamancompose.ui.component.ProgressBar
import com.example.ornamancompose.util.createCustomTempFile
import com.example.ornamancompose.util.showToast
import com.example.ornamancompose.util.uriToFile
import com.example.ornamancompose.viewmodel.ScanViewModel
import com.example.ornamancompose.viewmodel.ViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.io.File

private const val SCANSCREENTAG = "scan_screen_tag"

private lateinit var currentFilePath : String
private var lat : Double? = null
private var long : Double? = null

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    modifier : Modifier = Modifier,
    viewModel : ScanViewModel,
    scanResultAction : (PlantScanResponse, String, String) -> Unit
) {

    val context = LocalContext.current
    var uploadedFile by remember{
        mutableStateOf<File?>(null)
    }
    var isLoading by remember{
        mutableStateOf(false)
    }

    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissions)

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


    LaunchedEffect(Unit){
        multiplePermissionsState.launchMultiplePermissionRequest()
    }

    val fusedLocationClient = remember{
        LocationServices.getFusedLocationProviderClient(context)
    }

    val scanState by viewModel.scanPlantState.collectAsState()

    PermissionsRequired(
        multiplePermissionsState = multiplePermissionsState,
        permissionsNotGrantedContent = {
            // Not granted permission goes here
        },
        permissionsNotAvailableContent = {
            // If the user won't allow the permission content
        },
        content = {
            // Here
            // Todo(get the last location of user hasn't work yet)
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q){
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    context.startActivity(intent)
                }
            }
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { location ->
                if(location != null){
                    lat = location.latitude
                    long = location.longitude
                    Log.i("LOCATION-TAG", "lat : $lat \t long : $long")
                }
            }
        })

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        if(uploadedFile == null){
            Scan(
                onClickUploadGallery = {
                    launcherGallery.launch("image/*")
                },
                onClickTakePicture = {
                    takePhoto(context){intent ->
                        launcherIntentCamera.launch(intent)
                    }
                }
            )
        }else{
            Log.i("ScanScreen-TAG", "All Permissions Granted")
            // Request goes here
            if(lat != null && long != null){
                LaunchedEffect(Unit){
                    viewModel.scanPlant(uploadedFile!!)
                }
                LaunchedEffect(scanState){
                    when(scanState){
                        is UiState.Loading -> {
                            isLoading = true
                        }
                        is UiState.Exception -> {
                            isLoading = false
                            showToast(context, (scanState as UiState.Exception).message)
                        }
                        is UiState.Error -> {
                            isLoading = false
                            val message = "${(scanState as UiState.Error).code} : ${(scanState as UiState.Error).message}"
                            showToast(context, message)
                        }
                        is UiState.Success -> {
                            isLoading = false
                            scanResultAction(
                                (scanState as UiState.Success<PlantScanResponse>).data,
                                "$lat",
                                "$long"
                            )
                        }
                    }
                }
            }
        }

        if(isLoading){
            ProgressBar()
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
//        ScanScreen(
//            modifier = Modifier
//                .fillMaxSize()
//        )
//    }
//}

