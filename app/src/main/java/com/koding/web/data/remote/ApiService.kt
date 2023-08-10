package com.koding.web.data.remote

import com.koding.web.data.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("public/sliders")
    suspend fun getSlider(): Response<List<Sliders>>

    @GET("public/categories")
    suspend fun getCategory(
        @Query("page") page: Int
    ): Response<Paging<Category>>

    @GET("public/posts")
    suspend fun getArticle(
        @Query("page") page: Int
    ): Response<Paging<Article>>

    @GET("public/posts/{slug}")
    suspend fun getDetailArticle(
        @Path("slug") slug:String
    ):Response<Article>

    @GET("public/categories/{slug}")
    suspend fun getDetailCategory(
        @Path("slug") slug:String
    ):Response<Category>

    @GET("public/posts")
    suspend fun getArticle(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): Response<Paging<Article>>

}