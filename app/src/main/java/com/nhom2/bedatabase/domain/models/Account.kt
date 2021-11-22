package com.nhom2.bedatabase.domain.models

data class Account(
    val account_id: Int,
    val email: String,
    val password: String,
    val refreshToken: String? = null
)
