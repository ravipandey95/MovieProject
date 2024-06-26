package com.example.movieproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.databinding.FragmentMovieDetailsBinding
import com.example.movieproject.repository.data.ResponseMovieDetails
import com.example.movieproject.utils.MainStateEvent
import com.example.movieproject.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    override val layoutResId: Int
        get() = R.layout.fragment_movie_details
    private val movieDetailsViewModel : MovieDetailsViewModel by viewModels ()
    private var imdbId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imdbId = it.getString(IMDB_ID)
            Log.d("ravi", "Received IMDb ID: $imdbId")
        }
        movieDetailsViewModel.getMovieDetails(MainStateEvent.GetMovieDetailsResponse, imdbId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, observer)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.movie_detail)
    }

    private fun setDataInViews(movieDetails: ResponseMovieDetails){
        binding?.ivPoster?.let { Glide.with(requireActivity()).load(movieDetails.poster)
            .error(R.drawable.placeholder).into(it) }
        binding?.tvMovieName?.text = movieDetails.title
        binding?.tvDirector?.text = getString(R.string.directed_by, movieDetails.director)
        binding?.tvGenre?.text = movieDetails.genre
        binding?.tvSynopsis?.text = movieDetails.plot
    }

    override fun receivedResponse(item: Any?) {
        item?.let {response ->
            when(response){
                is ResponseMovieDetails ->{
                    setDataInViews(response)
                }
                else ->{

                }
            }
        }
    }

}