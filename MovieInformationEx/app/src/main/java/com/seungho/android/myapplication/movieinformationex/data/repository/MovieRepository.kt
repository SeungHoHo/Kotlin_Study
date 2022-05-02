package com.seungho.android.myapplication.movieinformationex.data.repository

import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieDto
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieModel
import com.seungho.android.myapplication.movieinformationex.data.retrofit.NetworkManager
import com.seungho.android.myapplication.movieinformationex.data.retrofit.RetrofitService
import retrofit2.Response

class MovieRepository {

    suspend fun getMovieAPI() : Response<MovieDto> {
        return NetworkManager.api.getMovieAPI()
    }
}