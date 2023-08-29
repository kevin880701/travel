package com.lhr.travel.data.calendar

import java.io.Serializable

data class CalendarResponse(val total: Int, val data: ArrayList<Data>)
data class Data(
    val distric: String,
    val address: String,
    val nlat: String,
    val elong: String,
    val tel: String,
    val fax: String,
    val ticket: String,
    val traffic: String,
    val parking: String,
    val is_major: Boolean,
    val id: Int,
    val title: String,
    val description: String,
    val begin: String,
    val end: String,
    val posted: String,
    val modified: String,
    val url: String,
    val files: ArrayList<Files>,
    val links: ArrayList<Links>
): Serializable
data class Files(val src: String, val subject: String, val ext: String): Serializable
data class Links(val src: String, val subject: String): Serializable