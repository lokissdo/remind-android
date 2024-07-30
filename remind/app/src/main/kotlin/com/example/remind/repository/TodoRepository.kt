package com.example.remind.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remind.helpers.TokenManager
import com.example.remind.model.TodoItem
import com.example.remind.network.todoservice.CreateTodoRequest
import com.example.remind.network.todoservice.CreateTodoRequestFromTodoItem
import com.example.remind.network.todoservice.CreateTodoResponse
import com.example.remind.network.todoservice.TodoService
import com.example.remind.network.todoservice.logRequestPayload
import com.example.remind.network.todoservice.toTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoRepository(private val context: Context, private val todoService: TodoService) {

    fun createTodoRequest(token: String, todoItem: TodoItem): LiveData<TodoItem?> {
        val result = MutableLiveData<TodoItem?>()

        val fcm_token = TokenManager.getToken(context)
        Log.d("fcm token", "FCM Token: $fcm_token")

        val createTodoRequest = CreateTodoRequestFromTodoItem(todoItem, fcm_token!!)

        logRequestPayload(createTodoRequest)

        val call: Call<Response<CreateTodoResponse>> = todoService.createTodo(token, createTodoRequest)
        call.enqueue(object : Callback<Response<CreateTodoResponse>> {
            override fun onResponse(
                call: Call<Response<CreateTodoResponse>>,
                response: Response<Response<CreateTodoResponse>>
            ) {
                // Log the raw response body
//                val rawResponseBody = response.errorBody()?.string() ?: response.body().toString()
//                Log.d("TodoRepository", "Raw Response Body: $rawResponseBody")
//
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.d("TodoRepository", "Mapped Response Body: $responseBody")
//
//                    val todoResponse = responseBody?.body()?.toTodo(context)
//                    result.postValue(todoResponse)
//                } else {
//                    Log.e("TodoRepository", "Error: ${response.errorBody()?.string()}")
//                    result.postValue(null)
//                }
            }

            override fun onFailure(call: Call<Response<CreateTodoResponse>>, t: Throwable) {
                t.printStackTrace()
                result.postValue(null)
            }
        })

        return result
    }
}
