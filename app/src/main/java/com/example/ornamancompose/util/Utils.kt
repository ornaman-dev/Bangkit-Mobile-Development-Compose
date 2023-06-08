package com.example.ornamancompose.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.widget.Toast
import androidx.annotation.StringRes
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date


fun styleStringResource(text : String) : String{
    val styledText = Html.fromHtml(text, FROM_HTML_MODE_LEGACY)
    return styledText.toString()
}

fun encodeStringUrl(url: String): String = URLEncoder.encode(url, "UTF-8")
fun decodeStringUrl(encodedUrl : String) : String = URLDecoder.decode(encodedUrl, "UTF-8")

fun showToast(context: Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun createCustomTempFile(context : Context) : File{
    val timeStamp : String = SimpleDateFormat("MM-DD-yyyy").format(Date())
    val storageDir : File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}