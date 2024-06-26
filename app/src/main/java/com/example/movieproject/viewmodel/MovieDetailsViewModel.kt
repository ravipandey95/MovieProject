package com.example.movieproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.repository.data.ResponseMovieDetails
import com.example.movieproject.usecase.MovieDetailsUseCase
import com.example.movieproject.utils.DataState
import com.example.movieproject.utils.MainStateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(application: Application,private val movieDetailsUseCase: MovieDetailsUseCase):
    AndroidViewModel(application) {

    private val _movieDetailsMutableLiveData = MutableLiveData<DataState<ResponseMovieDetails?>>()
    val movieDetailsLiveData : LiveData<DataState<ResponseMovieDetails?>>
        get() = _movieDetailsMutableLiveData

    fun getMovieDetails(mainStateEvent: MainStateEvent, imdbId: String?){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetMovieDetailsResponse -> {
                    movieDetailsUseCase.getMovieDetails(imdbId).onEach { dataState ->
                        _movieDetailsMutableLiveData.value = dataState
                    }.launchIn(viewModelScope)
                }
                else ->{

                }
            }
        }
    }



}