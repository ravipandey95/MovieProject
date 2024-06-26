package com.example.movieproject.repository

import android.util.Log
import com.example.movieproject.utils.BaseDataSource
import javax.inject.Inject

class MovieDataSource
@Inject
constructor(private val apiServices: ApiServices): BaseDataSource(){
    suspend fun getMoviesList(key: String, movie_type: String, page: Int, apikey: String) = invoke {
        apiServices.getMoviesList(key,movie_type,page,apikey)
    }
    suspend fun getMovieDetails(id: String?, apikey:String) = invoke {
        apiServices.getMovieDetails(id,apikey)
    }
}