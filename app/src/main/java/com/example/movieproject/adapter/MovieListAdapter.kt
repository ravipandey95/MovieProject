package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.adapter.viewholder.MoviesListViewHolder
import com.example.movieproject.databinding.MovieListAdapterBinding
import com.example.movieproject.repository.data.Movie
import com.example.movieproject.utils.RecyclerViewItemClickListener
import javax.inject.Inject

class MovieListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var clickListener: RecyclerViewItemClickListener<Movie>? = null
    private var items:MutableList<Movie?> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesListViewHolder(MovieListAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false),parent.context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currData = items[position]
        (holder as MoviesListViewHolder).bind(currData as Movie)
        holder.itemRowBinding.clickListener = clickListener
    }
    fun setOnClickListener(clickListener: RecyclerViewItemClickListener<Movie>?) {
        clickListener?.let {
            this.clickListener = it
        }
    }
    fun updateItems(newItems: List<Movie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}