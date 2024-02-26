package com.example.oniki.api

data class LoginResponse(
    val data: DataEnvelope
)

data class DataEnvelope(
    val user: User,
    )

data class RegisterResponse(
    val data: User
)
