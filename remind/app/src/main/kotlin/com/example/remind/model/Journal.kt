package com.example.remind.model

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import com.example.remind.util.uriToByteArray
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Journal(
    private var id: Long? = null,
    private var username: String,
    private var content: String,
    private var title: String,
    private var status: Boolean,
    private var updatedAt: Date? = null,
    private var images: List<Uri>? = null,
    private var audios: List<ByteArray>? = null,
) : Parcelable {

    // Getters
    fun getId(): Long? {
        return id;
    }

    fun getUsername(): String {
        return username
    }

    fun getContent(): String {
        return content
    }

    fun getTitle(): String {
        return title
    }

    fun getStatus(): Boolean {
        return status
    }

    fun getImages(): List<Uri>? {
        return images
    }

    fun getImagesString(): List<String> {
        return images.orEmpty().map { it.toString() }
    }

    fun getAudios(): List<ByteArray>? {
        return audios
    }

    fun getUpdatedAt(): Date? {
        return updatedAt
    }

    // Setters
    fun setId(id: Long) {
        this.id = id
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setStatus(status: Boolean) {
        this.status = status
    }

    fun setUpdatedAt(updatedAt: Date) {
        this.updatedAt = updatedAt
    }

    fun setImages(images: List<Uri>) {
        this.images = images
    }

    fun setAudios(audios: List<ByteArray>) {
        this.audios = audios;
    }

    fun getBytesOfImages(context: Context): List<ByteArray>? {
        return this.images?.mapNotNull { uri ->
            uriToByteArray(context, uri)
        }
    }
}
