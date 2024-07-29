package com.example.remind.network.authservice

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("v1/create_user")
    suspend fun createUser(
        @Body user: CreateUserRequest
    )

    @POST("v1/login_user")
    suspend fun loginUser(
        @Body user: LoginUserRequest
    ): Response<LoginUserResponse>
}