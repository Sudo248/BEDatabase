package com.nhom2.bedatabase.domain.models

data class Eng(
    val eng_id: Int,
    val group_id: Int,
    val pronunciation: String,
    val content: String,
    val type: String,
    val path_image: String
)
