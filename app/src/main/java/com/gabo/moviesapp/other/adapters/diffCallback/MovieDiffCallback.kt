package com.gabo.moviesapp.other.adapters.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.gabo.moviesapp.data.models.movieModels.MovieModel

class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}