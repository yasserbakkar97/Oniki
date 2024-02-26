package com.example.oniki.api

data class DefaultResponse (
    val data: DataEnvelope

)

data class RegisterBody(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val password: String,
    val password_confirmation: String,
    val accept_contact: Boolean
)