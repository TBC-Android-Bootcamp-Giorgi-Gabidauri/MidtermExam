package com.gabo.moviesapp.other.adapters.rvAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.PopularMovieItemBinding
import com.gabo.moviesapp.other.adapters.diffCallback.MovieDiffCallback
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL

class PopularMoviesAdapter(private val click: (MovieModel) -> Unit) :
    PagingDataAdapter<MovieModel, PopularMoviesAdapter.MovieVH>(MovieDiffCallback()) {
    private var genresList: List<GenreModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<GenreModel>) {
        genresList = list
        notifyDataSetChanged()
    }

    inner class MovieVH(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieModel, click: (MovieModel) -> Unit) {
            with(binding) {
                val genresAdapter = GenresAdapter()
                rvGenres.adapter = genresAdapter
                rvGenres.layoutManager = LinearLayoutManager(rvGenres.context,LinearLayoutManager.HORIZONTAL,false)
                val genresToFilter = genresList.toMutableList()
                val filtered = mutableListOf<GenreModel>()
                model.genreIds.forEach { genreId ->
                    genresToFilter.forEach { genreModel ->
                        if (genreId == genreModel.id) {
                            filtered.add(genreModel)
                        }
                    }
                }
                genresAdapter.submitList(filtered.toList())
                tvTitle.text = model.title
                tvRating.text = model.Rating.toString()
                Glide.with(ivPoster.context).load(BASE_IMAGE_URL + model.imageUrl)
                    .into(ivPoster)
                itemView.setOnClickListener { click(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val binding = PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position)!!, click)
    }
}