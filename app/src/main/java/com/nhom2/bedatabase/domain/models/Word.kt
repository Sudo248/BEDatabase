package com.nhom2.bedatabase.domain.models

import android.graphics.Bitmap

data class Word(
    var eng_id: Int,
    var eng: String,
    var vn: String,
    var image: Bitmap,
    var pronunciation: String
)