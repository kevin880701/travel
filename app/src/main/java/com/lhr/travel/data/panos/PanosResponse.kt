package com.lhr.travel.data.panos

data class PanosResponse(val total: Int, val data: ArrayList<Data>)
data class Data(
    val id: Int,
    val title: String,
    val url: String,
    val modified: String,
)