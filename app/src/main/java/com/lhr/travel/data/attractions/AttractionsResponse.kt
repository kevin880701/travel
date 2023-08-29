package com.lhr.travel.data.attractions

import java.io.Serializable

data class AttractionsResponse(val total: Int, val data: ArrayList<Data>)
data class Data(
    val id: Int,
    val name: String,
    val name_zh: String?,
    val open_status: Int,
    val introduction: String,
    val open_time: String,
    val zipcode: String,
    val distric: String,
    val address: String,
    val tel: String,
    val fax: String,
    val email: String,
    val months: String,
    val nlat: Float,
    val elong: Float,
    val official_site: String,
    val facebook: String,
    val ticket: String,
    val remind: String,
    val staytime: String,
    val modified: String,
    val url: String,
    val category: ArrayList<Category>,
    val target: ArrayList<Target>,
    val service: ArrayList<Service>,
    val friendly: ArrayList<Friendly>,
    val images: ArrayList<Images>,
    val files: ArrayList<Files>,
    val links: ArrayList<Links>
): Serializable

data class Category(val id: Int, val name: String): Serializable
data class Target(val id: Int, val name: String): Serializable
data class Service(val id: Int, val name: String): Serializable
data class Friendly(val id: Int, val name: String): Serializable
data class Images(val src: String, val subject: String, val ext: String): Serializable
data class Files(val src: String, val subject: String, val ext: String): Serializable
data class Links(val src: String, val subject: String): Serializable