package com.lhr.travel.data.audio

data class AudioResponse(val total: Int, val data: ArrayList<Data>)
data class Data(
    val id: Int,
    val title: String,
    val summary: String?,
    val url: String,
    val file_ext: String?,
    val modified: String,
)