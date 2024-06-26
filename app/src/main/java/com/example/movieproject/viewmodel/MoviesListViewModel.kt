package com.example.movieproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.repository.data.Movie
import com.example.movieproject.repository.data.ResponseMovieList
import com.example.movieproject.usecase.MoviesListUseCase
import com.example.movieproject.utils.DataState
import com.example.movieproject.utils.MainStateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject
constructor(application: Application, private val apiCallMoviesListUseCase: MoviesListUseCase ):
    AndroidViewModel(application) {

    var pageCount = 0
    private val maxPageCount = 10
    var viewPagerAlreadySet = false
    val finalMovieList = ArrayList<Movie>()
    val moviesViewPagerList = ArrayList<String>()

    private val _movieMutableLiveData = MutableLiveData<DataState<ResponseMovieList?>>()
    val movieListLiveData : LiveData<DataState<ResponseMovieList?>>
        get() = _movieMutableLiveData

    fun getMovieList(mainStateEvent: MainStateEvent){
        pageCount ++
        viewModelScope.launch{
            when(mainStateEvent){
                is MainStateEvent.GetMovieListResponse -> {
                    if(pageCount <= maxPageCount){
                        Log.d("ravi","InsideMovieListViemodel")
                        apiCallMoviesListUseCase.apiCallMovieList(pageCount).onEach { dataState ->
                            _movieMutableLiveData.value = dataState
                        }.launchIn(viewModelScope)
                    }
                }
                else -> {
                }
            }
        }
    }

    fun setDataForViewPager(){
        // This will only add 4 items in ViewPager
        run loop@{
            finalMovieList.forEach {
                if (moviesViewPagerList.size > 3) return@loop
                it.Poster?.let { it1 -> moviesViewPagerList.add(it1) }
            }
        }
    }
}