package com.example.remind.repository

import com.example.remind.network.authservice.AuthService
import com.example.remind.network.authservice.CreateUserRequest
import com.example.remind.network.authservice.LoginUserRequest
import com.example.remind.network.authservice.LoginUserResponse
import retrofit2.Response

class AuthRepository(
    private val authService: AuthService
) {
    suspend fun createUser(user: CreateUserRequest) {
        authService.createUser(user)
    }

    suspend fun loginUser(user: LoginUserRequest): Response<LoginUserResponse> {
        return authService.loginUser(user)
    }
}