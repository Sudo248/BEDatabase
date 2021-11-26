package com.nhom2.bedatabase.domain.models

data class Account(
    val account_id: Int? = null,
    val email: String? = null,
    val password: String? = null,
    val token: String? = null,
    val refreshToken: String? = null
)
