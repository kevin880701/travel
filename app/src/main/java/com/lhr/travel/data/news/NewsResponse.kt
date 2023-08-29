package com.lhr.travel.data.news

data class NewsResponse(val total: Int, val data: ArrayList<Data>)
data class Data(val id: Int,
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