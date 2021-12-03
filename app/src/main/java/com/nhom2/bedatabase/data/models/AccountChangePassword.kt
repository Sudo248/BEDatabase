package com.nhom2.bedatabase.data.models

data class AccountChangePassword(
    val account_id: Int? = null,
    val oldPassword: String? = null,
    val newPassword: String? = null,
    var message: String? = null
)
