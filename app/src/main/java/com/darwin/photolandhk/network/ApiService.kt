package com.darwin.photolandhk.network

import android.util.Log
import com.darwin.photolandhk.posts.Category
import com.darwin.photolandhk.posts.CategoryCount
import com.darwin.photolandhk.posts.PostContent
import com.darwin.photolandhk.posts.Thumbnail
import com.darwin.photolandhk.product_library.ProductSimple
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException


enum class ApiFilter(val value: String?) {
    SHOW_ALL("全部文章"),
    SHOW_NEWS("攝影大事報"),
    SHOW_REPORT("真．用家報告"),
    SHOW_DISCUSSION("攝影齊齊傾")
}

//private val BASE_URL = "https://photolandhk.com/"
private val BASE_URL = "https://cshinestaging2.wpengine.com/"

var PRODUCT_PERPAGE = 30

lateinit var product_categories: List<String>

object PostCountCallable {
    var count = 0
    fun getCount(categoryId: Int? = null): Int {
        call(categoryId)
        return count
    }

    private fun call(categoryId: Int? = null) {
//        val SDK_INT = Build.VERSION.SDK_INT
//        if (SDK_INT > 8) {
//            val policy = StrictMode.ThreadPolicy.Builder()
//                .permitAll().build()
//            StrictMode.setThreadPolicy(policy)
        val client = OkHttpClient()
        var url = BASE_URL + "wp-json/wp/v2/posts?per_page=1&_fields=id"
        categoryId?.let { url += "&categories=$it" }
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val msg = e.toString()
                Log.e("OkHttp", msg)
                count = 0
            }

            override fun onResponse(call: Call, response: Response) {
                val msg = response.toString()
                count = (response.headers()["x-wp-total"] as String).toInt()
                Log.i("OkHttp", msg)
            }
        })
    }
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


/**
 * A public interface that exposes the [getCategories], [getPostList], [getPost] methods
 */
interface ApiService {

    @GET("wp-json/wp/v2/categories")
    suspend fun getCategories(
        @Query("per_page") pages: Int = 100,
        @Query("_fields") fields: String = "id,name,count"
    ): List<Category>

    @GET("wp-json/wp/v2/categories/{category}?_fields=count")
    suspend fun getCategoryPostCount(@Path("category") id: Int): CategoryCount

    @GET("wp-json/wp/v2/posts")
    suspend fun getPostList(
        @Query("per_page") per_page: Int = LOAD_PER_TIME,
        @Query("page") page: Int = 1,
        @Query("categories") category: Int,
        @Query("_fields") fields: String = "id,date,modified,title,content,author,categories,tags,author_info,featured_media,featured_image_src"
    ): List<PostContent>

    @GET("wp-json/wp/v2/posts")
    suspend fun getAllPostList(
        @Query("per_page") per_page: Int = LOAD_PER_TIME,
        @Query("page") page: Int = 1,
        @Query("_fields") fields: String = "id,date,modified,title,content,author,categories,tags,author_info,featured_media,featured_image_src"
    ): List<PostContent>

    @GET("wp-json/wp/v2/users/{id}")
    suspend fun getAuthorThumbnail(
        @Path("id") id: Int = 7,
        @Query("_fields") fields: String = "avatar_urls"
    ): Thumbnail

    @GET("wp-json/ap/v1/search")
    suspend fun getProductList(
        @Query("filters") filters: String = "category.lens",
        @Query("page") page: Int = 1
    ): List<ProductSimple>

    @GET("wp-json/ap/v1/perpage")
    suspend fun getProductPerPage(): Int

    @GET("wp-json/ap/v1/count")
    suspend fun getProductCount(): Int

}

val categoryList = mapOf(
    "Uncategorized" to 1,
    "攝影分享" to 112,
    "攝影大事報" to 6,
    "攝影教學" to 111,
    "攝影齊齊傾" to 108,
    "新機傳聞" to 679,
    "新機發佈" to 682,
    "新鏡傳聞" to 680,
    "新鏡發佈" to 681,
    "活動報名站" to 109,
    "濾鏡報告" to 176,
    "焦點文章" to 821,
    "產品介紹" to 169,
    "相機報告" to 757,
    "相機袋報告" to 170,
    "真．用家報告" to 166,
    "穩定器報告" to 175,
    "腳架報告" to 173,
    "航拍機報告" to 174,
    "配件報告" to 172,
    "鏡頭報告" to 171
)

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

enum class ApiStatus { LOADING, ERROR, DONE }

val homeOverviewListSize = 12
val LOAD_PER_TIME = 30