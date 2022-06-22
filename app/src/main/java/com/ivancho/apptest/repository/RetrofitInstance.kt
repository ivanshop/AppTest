package com.ivancho.apptest.repository

import com.ivancho.apptest.api.ApiService
import com.ivancho.apptest.utils.Constants.Companion.BASE_URL_POSTS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_POSTS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}