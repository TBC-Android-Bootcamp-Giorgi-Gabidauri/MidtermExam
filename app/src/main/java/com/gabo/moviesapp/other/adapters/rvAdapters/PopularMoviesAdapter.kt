package com.gabo.moviesapp.other.adapters.rvAdapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.PopularMovieItemBinding
import com.gabo.moviesapp.other.adapters.diffCallback.MovieDiffCallback
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.loadImage
import com.gabo.moviesapp.other.common.loadImageDecreasedQuality
import com.gabo.moviesapp.other.common.setupGenres

class PopularMoviesAdapter(
    private val itemClick: (MovieModel) -> Unit
) :
    PagingDataAdapter<MovieModel, PopularMoviesAdapter.MovieVH>(MovieDiffCallback()) {
    private var genresList: List<GenreModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<GenreModel>) {
        genresList = list
        notifyDataSetChanged()
    }

    inner class MovieVH(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieModel, itemClick: (MovieModel) -> Unit) {
            with(binding) {
                rvGenres.setupGenres(genresList, model)
                tvTitle.text = model.title
                tvRating.text = model.Rating.toString()
                ivPoster.loadImageDecreasedQuality(BASE_IMAGE_URL + model.imageUrl)
                itemView.setOnClickListener { itemClick(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val binding =
            PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position)!!, itemClick)
    }
}