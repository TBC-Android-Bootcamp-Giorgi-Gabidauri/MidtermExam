package com.gabo.moviesapp.other.adapters.rvAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.NowPlayingMovieItemBinding
import com.gabo.moviesapp.other.adapters.diffCallback.MovieDiffCallback
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL

class NowPlayingMoviesAdapter(private val click: (MovieModel) -> Unit) :
    PagingDataAdapter<MovieModel, NowPlayingMoviesAdapter.MovieVH>(MovieDiffCallback()) {

    inner class MovieVH(private val binding: NowPlayingMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieModel, click: (MovieModel) -> Unit) {
            with(binding) {
                tvTitle.text = model.title
                tvRating.text = model.Rating.toString()
                Glide.with(ivPoster.context).load(BASE_IMAGE_URL + model.imageUrl)
                    .into(ivPoster)
                itemView.setOnClickListener { click(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val binding = NowPlayingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position)!!, click)
    }
}