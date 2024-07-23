package com.example.remind.model

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
class Journal(
    private var content: String,
    private var title: String,
    private var updatedAt: Date,
    private var status: String,
    private var images: List<String>
) : Parcelable {

    // Getters
    fun getContent(): String {
        return content
    }

    fun getTitle(): String {
        return title
    }

    fun getUpdatedAt(): Date {
        return updatedAt
    }

    fun getStatus(): String {
        return status
    }
    fun getImages(): List<String> {
        return images
    }

    // Setters
    fun setContent(content: String) {
        this.content = content
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setUpdatedAt(updatedAt: Date) {
        this.updatedAt = updatedAt
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun setImages(images: List<String>) {
        this.images = images
    }
}
