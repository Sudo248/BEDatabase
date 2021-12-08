package com.nhom2.bedatabase.data.models

import com.nhom2.bedatabase.domain.models.Vn

data class PostEng(
    val user_id: Int,
    var group_id: Int,
    var pronunciation: String,
    var content: String,
    var type: String,
    var path_image: String? = null,
    var vns: List<Vn> = listOf()
)
