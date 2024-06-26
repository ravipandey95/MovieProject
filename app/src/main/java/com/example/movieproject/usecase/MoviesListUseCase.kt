package com.example.movieproject.usecase

import com.example.movieproject.di.Constants
import com.example.movieproject.repository.MovieDataSource
import com.example.movieproject.repository.data.ResponseMovieList
import com.example.movieproject.utils.DataState
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesListUseCase
@Inject
constructor(private val movieDataSource: MovieDataSource){
    suspend fun apiCallMovieList(currentPage: Int): Flow<DataState<ResponseMovieList?>> = flow {
        emit(DataState.Loading)
        val response = movieDataSource.getMoviesList(
            Constants.key,
            Constants.movie_type,
            currentPage,
            Constants.apiKey
        )
        when(response.statusCode){
            200 -> emit(DataState.Success(response.data,response.statusCode))
            else -> emit(DataState.Error(response.statusCode,null,response.errorMsg))
        }
    }
}