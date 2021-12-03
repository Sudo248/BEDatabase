package com.nhom2.bedatabase.domain.models

data class Eng(
    var eng_id: Int? = null,
    var group_id: Int,
    var pronunciation: String,
    var content: String,
    var type: String,
    var path_image: String? = null,
    var vns: List<Vn> = listOf()
)
