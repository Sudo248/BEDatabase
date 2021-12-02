package com.nhom2.bedatabase.domain.models

data class User(
    val user_id: Int,
    val user_name: String,
    val path_image: String? = null
)
