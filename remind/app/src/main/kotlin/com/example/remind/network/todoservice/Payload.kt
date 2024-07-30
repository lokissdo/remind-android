package com.example.remind.network.todoservice

import android.content.Context
import android.util.JsonToken
import android.util.Log
import com.example.remind.model.TodoItem
import com.example.remind.network.ByteArrayDeserializer
import com.example.remind.network.ByteArraySerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Data class representing the details of a Todo
data class TodoDetails(
    val id: String,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val userId: String,
    val reminders: List<Reminder>
)

// Data class representing a Reminder
data class Reminder(
    val id: String,
    val start: String,
    val message: String,
    val todoId: String,
    val userId: String,
    val sent: Boolean
)

// Data class representing the request for creating a Todo
data class CreateTodoRequest(
    val todo: TodoDetails,
    val username: String,
    val FCMToken: String
)

// Data class representing the response for creating a Todo
data class CreateTodoResponse(
    val todo: TodoDetails
)

// Extension function to convert CreateTodoResponse to TodoItem
fun CreateTodoResponse.toTodo(context: Context): TodoItem {
    val timeFormat = SimpleDateFormat("H'h'mm", Locale.getDefault())
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return TodoItem(
        title = this.todo.title,
        category = "HEALTH",
        description = this.todo.description,
        status = "PENDING",
        startTime = timeFormat.format(this.todo.startTime),
        endTime = timeFormat.format(this.todo.endTime),
        date = dateFormat.format(this.todo.startTime)
    )
}
fun CreateTodoRequestFromTodoItem(todoItem: TodoItem, fcmToken: String): CreateTodoRequest {
    Log.d("toDoData", todoItem.toString())

    // Define the date and time formats
    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("EEE, dd MMM HH:mm", Locale.getDefault())
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    // Parse the date and time strings from TodoItem
    val date = dateFormat.parse(todoItem.date)
    val startTime = timeFormat.parse(todoItem.startTime)
    val endTime = timeFormat.parse(todoItem.endTime)

    // Combine date and time into a single Date object for startTime and endTime
    val startDateTime = Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, startTime.hours)
        set(Calendar.MINUTE, startTime.minutes)
    }.time

    val endDateTime = Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, endTime.hours)
        set(Calendar.MINUTE, endTime.minutes)
    }.time

    val reminderStartTime = Calendar.getInstance().apply {
        time = startDateTime
        add(Calendar.MINUTE, -30)
    }.time
    val reminder = Reminder(
        id = "",
        start = isoFormat.format(reminderStartTime),
        message = "Reminder: ${todoItem.title} starts in 30 minutes",
        todoId = "",
        userId = "",
        sent = false
    )

    return CreateTodoRequest(
        todo = TodoDetails(
            id = "",
            title = todoItem.title,
            description = todoItem.description,
            startTime =  isoFormat.format(startDateTime),
            endTime = isoFormat.format(endDateTime),
            userId = "",
            reminders = listOf(reminder)
        ),
        username = "quang3",
        FCMToken = fcmToken
    )
}


// Data class representing the request for updating a Todo
data class UpdateTodoRequest(
    val id: String,
    val username: String,
    val title: String? = null,
    val description: String? = null,
    val startTime: Date? = null,
    val endTime: Date? = null,
    val userId: String? = null,
    val reminders: List<Reminder>? = null
)

// Data class representing the response for updating a Todo
data class UpdateTodoResponse(
    val success: Boolean,
    val message: String? = null
)

// Data class representing the response for fetching all Todos
data class GetAllTodosResponse(
    val todos: List<TodoDetails>
)

// Function to log the request payload
fun logRequestPayload(request: CreateTodoRequest) {
    val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, ByteArraySerializer())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayDeserializer())
        .create()
    val json = customGson.toJson(request)
    Log.d("RequestPayload", json)
}
