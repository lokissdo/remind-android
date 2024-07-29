package com.example.remind.model

import java.util.Date

data class Image (
    val id: Long,
    val journalId: Long,
    val content: ByteArray,
    val createdAt: Date
)