package com.example.movieproject.utils

sealed class MainStateEvent {
    object GetMovieListResponse : MainStateEvent()
    object GetMovieDetailsResponse : MainStateEvent()
}