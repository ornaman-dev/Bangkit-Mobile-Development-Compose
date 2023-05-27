package com.example.ornamancompose.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun createCustomTempFile(context : Context) : File{
    val timeStamp : String = SimpleDateFormat("MM-DD-yyyy").format(Date())
    val storageDir : File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}