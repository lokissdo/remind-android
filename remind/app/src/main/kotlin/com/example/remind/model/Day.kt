package com.example.remind.model

data class Day(
    val date: String,
    val todos: MutableList<TodoItem>,
    var isChosen: Boolean = false
)