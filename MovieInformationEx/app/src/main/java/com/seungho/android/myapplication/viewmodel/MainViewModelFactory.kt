package com.seungho.android.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seungho.android.myapplication.movieinformationex.data.repository.MovieRepository

class MainViewModelFactory(
    private val movieRepository: MovieRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(movieRepository) as T
    }
}