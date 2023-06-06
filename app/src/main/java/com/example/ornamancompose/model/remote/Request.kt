package com.example.ornamancompose.model.remote

data class RegisterRequestBody(
    val username : String,
    val email : String,
    val password : String
)