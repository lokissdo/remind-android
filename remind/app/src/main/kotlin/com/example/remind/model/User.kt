package com.example.remind.model

import java.util.Date

class User (
    val username: String,
    val fullName: String,
    val email: String,
    val passwordChangedAt: Date,
    val createdAt: Date
)

