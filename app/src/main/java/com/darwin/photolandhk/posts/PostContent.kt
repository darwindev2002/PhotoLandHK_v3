package com.darwin.photolandhk.posts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Category (
    val id: Int,
    val name: String,
    val count: Int
)

data class CategoryCount (
    val count: Int
)

@Parcelize
data class PostContent(
    val id: Int,
    val date: String,  // "2020-11-05T12:21:00"
    val modified: String,  // "2020-11-05T12:22:12"
    val title: Title, // {rendered: "ON1 Photo RAW 2021 已完全整合 Portrait AI 功能"}
    val content: Content,  // {false,…}
    val author: Int,  // 7
    val featured_media: Int,  // 10503
    val categories: List<Int>,  // [6]
    val tags: List<Int>,  // [522, 991, 994, 534, 986, 990, 523, 1477]
    val featured_image_src: String? = null,  // "https://photolandhk.com/wp-content/uploads/2020/11/16-9-3-scaled.jpg"
    val author_info: AuthorInfo  // {display_name: "Perry Yu", author_link: "https://photolandhk.com/author/perryyu/"}
): Parcelable

@Parcelize
class Title(
    // {rendered: "ON1 Photo RAW 2021 已完全整合 Portrait AI 功能"}
    val rendered: String  // "ON1 Photo RAW 2021 已完全整合 Portrait AI 功能"
): Parcelable

@Parcelize
class Content(
    // {false,…}
    val protected: Boolean,  // false
    val rendered: String  // "..."
): Parcelable

@Parcelize
class AuthorInfo(
    // {display_name: "Perry Yu", author_link: "https://photolandhk.com/author/perryyu/"}
    val display_name: String,  // "Perry Yu"
    val author_link: String  // "https://photolandhk.com/author/perryyu/"
): Parcelable

@Parcelize
class Thumbnail(
    val avatar_urls: Map<Int, String>
) : Parcelable