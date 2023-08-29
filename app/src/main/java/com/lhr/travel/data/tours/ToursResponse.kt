package com.lhr.travel.data.tours

import java.io.Serializable

data class ToursResponse(val total: Int, val data: ArrayList<Data>)
data class Data(val id: Int,
                val seasons: ArrayList<String>,
                val months: ArrayList<String>,
                val days: Int,
                val title: String,
                val author: String,
                val description: String,
                val consume: String,
                val remark: String,
                val note: String,
                val url: String,
                val category: ArrayList<Categorys>,
                val transport: ArrayList<Transports>,
                val users: ArrayList<Users>,
                val modified: String,
                val files: ArrayList<Files>
    ): Serializable
data class Files(val src: String, val subject: String, val ext: String): Serializable
data class Categorys(val id: Int, val name: String): Serializable
data class Transports(val id: Int, val name: String): Serializable
data class Users(val id: Int, val name: String): Serializable
