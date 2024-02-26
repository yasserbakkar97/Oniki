package com.example.oniki.api

data class User(
    val id: Int,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val phone_number: String?,
    val password: String?,
    val password_confirmation: String?,
    val accept_contact: Boolean
)

data class UserToken (
    val access_token : String?
        )