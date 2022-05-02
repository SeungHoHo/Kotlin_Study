package com.seungho.android.myapplication.movieinformationex.data.retrofit

import com.seungho.android.myapplication.movieinformationex.data.retrofit.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkManager {
    //    private val retrofit =
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}