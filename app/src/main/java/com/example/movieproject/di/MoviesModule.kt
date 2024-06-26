package com.example.movieproject.di

import com.example.movieproject.repository.MovieDataSource
import com.example.movieproject.usecase.MovieDetailsUseCase
import com.example.movieproject.usecase.MoviesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class MoviesModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieListUseCase(movieDataSource: MovieDataSource): MoviesListUseCase{
        return MoviesListUseCase(movieDataSource)
    }
    @Provides
    @ActivityRetainedScoped
    fun provideMovieDetailsUseCase(movieDataSource: MovieDataSource): MovieDetailsUseCase{
        return MovieDetailsUseCase(movieDataSource)
    }
}