package com.example.remind.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.Base64

class ByteArraySerializer : JsonSerializer<ByteArray> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(src: ByteArray, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val base64String = Base64.getEncoder().encodeToString(src)
        return context.serialize(base64String)
    }
}

