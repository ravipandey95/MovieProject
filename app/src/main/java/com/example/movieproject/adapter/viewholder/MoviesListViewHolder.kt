package com.example.movieproject.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.BR
import com.example.movieproject.R
import com.example.movieproject.databinding.MovieListAdapterBinding
import com.example.movieproject.repository.data.Movie

class MoviesListViewHolder (val itemRowBinding: MovieListAdapterBinding, val context: Context):
RecyclerView.ViewHolder(itemRowBinding.root){
    fun bind(item: Movie){
        itemRowBinding.movieList = item
        Glide.with(context).load(item.Poster).error(R.color.purple_200).into(itemRowBinding.iv1)
    }
}