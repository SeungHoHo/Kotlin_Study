package com.seungho.android.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seungho.android.myapplication.movieinformationex.data.repository.MovieRepository
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieDto
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val myResponse : MutableLiveData<Response<MovieDto>> = MutableLiveData()

    fun getMovieAPI() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMovieAPI()
                myResponse.value = response
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}