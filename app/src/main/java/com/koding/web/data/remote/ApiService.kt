package com.koding.web.data.remote

import com.koding.web.data.remote.model.Response
import com.koding.web.data.remote.model.Sliders
import retrofit2.http.GET

interface ApiService {
    @GET("public/sliders")
    suspend fun getSlider(): Response<List<Sliders>>
}