package com.example.remind.network.journalservice

import android.content.Context
import android.util.Log
import com.example.remind.model.Image
import com.example.remind.model.Journal
import com.example.remind.network.ByteArrayDeserializer
import com.example.remind.network.ByteArraySerializer
import com.google.firebase.Timestamp

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.remind.util.toUriList
import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class JournalDetails (
    val id: Long,
    val username: String,
    val title: String,
    val content: String,
    val status: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)

data class CreateJournalRequest(
    val username: String,
    val title: String,
    val content: String,
    val status: Boolean,
    val images: List<ByteArray>? = null,
    val audios: List<ByteArray>? = null
)

data class CreateJournalResponse(
    val journal: JournalDetails,
    val images: List<Image>? = null,
    val audios: List<ByteArray>? = null
)

fun CreateJournalResponse.toJournal(context: Context): Journal {
    return Journal(
        id = this.journal.id,
        username = this.journal.username,
        title = this.journal.title,
        content = this.journal.content,
        status = this.journal.status,
        updatedAt = this.journal.updatedAt,
        images = this.images?.toUriList(context),
        audios = this.audios
    )
}

data class UpdateJournalRequest(
    val id: String,
    val username: String,
    val title: String? = null,
    val content: String? = null,
    val status: String? = null,
)

data class UpdateJournalResponse(
    val success: Boolean,
    val message: String? = null
)

data class GetAllJournalsResponse(
    val journals: List<JournalDetails>,
    val images: List<Image>,
)

data class GetJournalsResponse(
    val imageId: List<Long>,
    val journalId: List<Long>
)

fun logRequestPayload(request: CreateJournalRequest) {
    val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, ByteArraySerializer())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayDeserializer())
        .create()
    val json = customGson.toJson(request)
    Log.d("RequestPayload", json)
}

