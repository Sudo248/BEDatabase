package com.nhom2.bedatabase.domain.models

data class User(
    val user_id: Int,
    var user_name: String,
    var path_image: String? = null
)
