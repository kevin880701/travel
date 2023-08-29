package com.lhr.travel.data.activitys

data class ActivitysResponse(val total: Int, val data: ArrayList<Data>)
data class Data(
    val distric: String,
    val address: String,
    val nlat: String,
    val elong: String,
    val organizer: String,
    val co_rganizer: String,
    val contact: String,
    val tel: String,
    val fax: String,
    val ticket: String,
    val traffic: String,
    val parking: String,
    val id: Int,
    val title: String,
    val description: String,
    val begin: String?,
    val end: String?,
    val posted: String,
    val modified: String,
    val url: String,
    val files: ArrayList<Files>,
    val links: ArrayList<Links>
)

data class Files(val src: String, val subject: String, val ext: String)
data class Links(val src: String, val subject: String)