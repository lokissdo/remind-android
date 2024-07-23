package com.example.remind.model

data class Day(
    val date: String,
    val todos: List<TodoItem>,
    var isChosen: Boolean = false
)