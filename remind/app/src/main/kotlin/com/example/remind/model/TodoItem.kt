package com.example.remind.model

data class TodoItem(
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val category: String,
    val startTime : String,
    val endTime : String
)

