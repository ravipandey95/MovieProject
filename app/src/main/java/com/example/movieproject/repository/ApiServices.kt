package com.example.movieproject.repository

import com.example.movieproject.repository.data.ResponseMovieDetails
import com.example.movieproject.repository.data.ResponseMovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/")
    suspend fun getMoviesList(
        @Query("s") key: String,
        @Query("type") movie_type: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String
    ):Response<ResponseMovieList>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") id: String?,
        @Query("apikey") apikey: String
    ):Response<ResponseMovieDetails>
}