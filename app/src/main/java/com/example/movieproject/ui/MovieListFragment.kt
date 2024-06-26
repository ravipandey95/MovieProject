package com.example.movieproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieproject.R
import com.example.movieproject.adapter.MovieListAdapter
import com.example.movieproject.adapter.MoviesViewPagerAdapter
import com.example.movieproject.databinding.FragmentMovieListBinding
import com.example.movieproject.repository.data.Movie
import com.example.movieproject.repository.data.ResponseMovieList
import com.example.movieproject.utils.EndlessRecyclerOnScrollListener
import com.example.movieproject.utils.MainStateEvent
import com.example.movieproject.utils.RecyclerViewItemClickListener
import com.example.movieproject.viewmodel.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment() : BaseFragment<FragmentMovieListBinding>(),RecyclerViewItemClickListener<Movie> {
    override val layoutResId: Int
        get() = R.layout.fragment_movie_list
    private val movieListViewModel: MoviesListViewModel by viewModels()
    private lateinit var navController: NavController
    private var movieAdapter: MovieListAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null
    private var viewPagerAdapter: MoviesViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListViewModel.getMovieList(MainStateEvent.GetMovieListResponse)
        Log.d("ravi","OnCreate in MovieListFragment")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        movieListViewModel.movieListLiveData.observe(viewLifecycleOwner, observer)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.movie)
        initializeViewPagerAndRecyclerView()
        setAdapter()

        Log.d("ravi","OnViewCreated in MovieListFragment")
        binding?.rvParent?.addOnScrollListener(object:
        EndlessRecyclerOnScrollListener(gridLayoutManager!!){
            override fun onScrolledToEnd() {
                movieListViewModel.getMovieList(MainStateEvent.GetMovieListResponse)
            }
        })
    }
    private fun initializeViewPagerAndRecyclerView(){
        movieAdapter = null
        movieListViewModel.viewPagerAlreadySet=false
        gridLayoutManager = GridLayoutManager(requireActivity(), 3)
        viewPagerAdapter=MoviesViewPagerAdapter(context)
        binding?.viewpager?.adapter=viewPagerAdapter
        binding?.viewpager?.pageMargin=20
        binding?.viewpager?.clipToPadding=false
    }

    fun setAdapter(){
        if(!movieListViewModel.viewPagerAlreadySet){
            movieListViewModel.viewPagerAlreadySet = true
            viewPagerAdapter!!.refreshImageCarousel(movieListViewModel.moviesViewPagerList)
        }
        if(movieAdapter == null){
            movieAdapter = MovieListAdapter()
            movieAdapter!!.setOnClickListener(this)
            binding?.rvParent?.layoutManager = gridLayoutManager
            binding?.rvParent?.adapter = movieAdapter
            Log.d("ravi","Adapter set in MovieListFragment")
        }
        else{
            movieAdapter?.notifyDataSetChanged()
        }
    }
    override fun receivedResponse(item: Any?) {
        Log.d("ravi","recievedResponse")
        item?.let {response ->
            when(response){
                is ResponseMovieList ->{
                    binding?.tvNowShowing?.visibility = View.VISIBLE
                    response.movieList?.let { it1 ->
                        movieListViewModel.finalMovieList.addAll(it1)

                        if (movieListViewModel.pageCount == 1){
                            movieListViewModel.setDataForViewPager()
                        }
                        movieAdapter?.updateItems(movieListViewModel.finalMovieList)
                    }
                }
                else ->{

                }
            }
        }
    }

    override fun onClick(item: Movie) {
        val bundle = Bundle()
        Log.d("ravi", "Navigating to details with IMDb ID: ${item.imdbID}")
        bundle.putString(IMDB_ID,item.imdbID)
        navController.navigate(R.id.action_movieList_to_movieDetails,bundle)
    }
}
