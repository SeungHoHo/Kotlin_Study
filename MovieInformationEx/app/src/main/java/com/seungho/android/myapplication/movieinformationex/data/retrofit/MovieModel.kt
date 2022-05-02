package com.seungho.android.myapplication.movieinformationex.data.retrofit

import java.io.Serializable

data class MovieModel(
    val title : String,
    val gerne : String,
    val time : Int,
    val date: String,
    val rate : Float,
    val grade: Int,
    val director : String,
    val actor: String,
    val synopsis: String,
    val poster: String,
    val stillCut: String,
    val video: String,
    val watcher: Int
) : Serializable

data class MovieDto(
    val movies : List<MovieModel>
)
