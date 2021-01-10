package com.darwin.photolandhk.product_library

data class ProductSimple(
    val ID: Int,
    val post_title: String,
    val post_date_gmt: String
)

data class ProductDetailed(
    val id: Int,
    val name: String,
    val views: Int,
    val thm: String
)