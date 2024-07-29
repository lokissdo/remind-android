package com.example.remind.network.authservice

import com.example.remind.model.User

data class CreateUserRequest (
    val username: String,
    val fullName: String,
    val email: String,
    val password: String
)

data class LoginUserRequest (
    val username: String,
    val password: String
)

data class LoginUserResponse (
    val user: User,
    val accessToken: String
)