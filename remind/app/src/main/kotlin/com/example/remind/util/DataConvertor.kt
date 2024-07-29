package com.example.remind.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.remind.model.Image
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val byteBuffer = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            byteBuffer.toByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveByteArrayToFile(context: Context, byteArray: ByteArray, fileName: String): Uri {
    val file = File(context.cacheDir, fileName)
    Log.d("image-cached", file.toString())
    FileOutputStream(file).use { fos ->
        fos.write(byteArray)
    }
    return Uri.fromFile(file)
}

fun List<Image>.toUriList(context: Context): List<Uri> {
    return this.map { image ->
        saveByteArrayToFile(context, image.content, "${image.id}.jpeg")
    }
}
