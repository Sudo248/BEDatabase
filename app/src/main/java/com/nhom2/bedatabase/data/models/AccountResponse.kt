package com.nhom2.bedatabase.data.models

data class AccountResponse(
    val message: String,
    val account_id: Int? = null,
    val token: String? = null
)