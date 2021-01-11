package com.darwin.photolandhk.product_library

data class ProductSimple(
    val pid: Int,
    val title: String,
    val thumb: Thumb
)

data class Thumb(
    val url: String,
    val width: Int,
    val height: Int,
    val not_found: String
)

data class ProductDetailed(
    val id: Int,
    val name: String,
    val views: Int,
    val thm: String
)