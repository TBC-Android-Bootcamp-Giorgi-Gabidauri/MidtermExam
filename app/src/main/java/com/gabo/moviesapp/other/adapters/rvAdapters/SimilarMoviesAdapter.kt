package com.gabo.moviesapp.other.adapters.rvAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.PopularMovieItemBinding
import com.gabo.moviesapp.other.adapters.diffCallback.MovieDiffCallback
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.loadImage
import com.gabo.moviesapp.other.common.setupGenres

class SimilarMoviesAdapter(
    private val itemClick: (MovieModel) -> Unit
) :
    RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesVH>() {
    private var genresList: List<GenreModel> = emptyList()
    private var list: List<MovieModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MovieModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitGenresList(list: List<GenreModel>) {
        genresList = list
        notifyDataSetChanged()
    }

    inner class SimilarMoviesVH(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: MovieModel,
            itemClick: (MovieModel) -> Unit
        ) {
            with(binding) {
                rvGenres.setupGenres(genresList, model)
                tvTitle.text = model.title
                tvRating.text = model.Rating.toString()
                ivPoster.loadImage(model.imageUrl!!)
                itemView.setOnClickListener { itemClick(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesVH {
        val binding =
            PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMoviesVH(binding)
    }

    override fun onBindViewHolder(holder: SimilarMoviesVH, position: Int) {
        val item = list[position]
        holder.bind(item, itemClick)
    }

    override fun getItemCount() = list.size

}