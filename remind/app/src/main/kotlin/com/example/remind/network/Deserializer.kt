package com.example.remind.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Base64

class ByteArrayDeserializer : JsonDeserializer<ByteArray> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ByteArray {
        val base64String = json.asString
        return Base64.getDecoder().decode(base64String)
    }
}