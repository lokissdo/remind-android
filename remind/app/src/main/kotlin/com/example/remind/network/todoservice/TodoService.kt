package com.example.remind.network.todoservice;

import com.example.remind.network.journalservice.GetAllJournalsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TodoService {

    @POST("todo/v1/create_todo")
    fun  createTodo(
            @Header("Authorization") token:String,
            @Body todoCreateRequest:CreateTodoRequest
    ):Call<Response<CreateTodoResponse>>

    @GET("todo/v1/list_todos")
    suspend fun getAllTodos(
        @Header("Authorization") token: String,
        @Query("username") username: String,
    ): Call<Response<GetAllTodosResponse>>
}