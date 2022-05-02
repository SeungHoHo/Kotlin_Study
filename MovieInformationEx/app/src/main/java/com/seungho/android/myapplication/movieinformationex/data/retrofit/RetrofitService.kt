package com.seungho.android.myapplication.movieinformationex.data.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("/v3/c30627dd-8812-4c92-86e6-b865c9a29220")
//    fun getMovieAPI(): Call<MovieDto>
    suspend fun getMovieAPI() : Response<MovieDto>
}