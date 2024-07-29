package com.example.remind.adapter

import com.example.remind.model.TodoItem

interface TodoClickListener {
    fun onTodoClick(todo: TodoItem)
}
