package com.gabo.moviesapp.other.common

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.NowStreamingMovieItemBinding
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleOwner.launchStarted(block: suspend (CoroutineScope) -> (Unit)): Job {
    return this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block.invoke(this@launch)
        }
    }
}

fun <VH : RecyclerView.ViewHolder, A> RecyclerView.setupAdapter(
    rvAdapter: RecyclerView.Adapter<VH>,
    layoutManager: RecyclerView.LayoutManager
): A {
    this.adapter = rvAdapter
    this.layoutManager = layoutManager
    return rvAdapter as A
}

fun NowStreamingMovieItemBinding.setNowStreamingData(
    model: MovieModel,
    click: (MovieModel) -> Unit
) {
    with(this) {
        tvTitle.text = model.title
        tvRating.text = model.Rating.toString()
        ivPoster.loadImage(model.imageUrl!!)
        this.root.setOnClickListener { click(model) }
    }
}

fun ImageView.loadImage(imgUrl: String) {
    Glide.with(this).load(BASE_IMAGE_URL + imgUrl).into(this)
}

fun ImageView.loadImageDecreasedQuality(imgUrl: String) {
    Glide.with(this).load(BASE_IMAGE_URL + imgUrl)
        .apply(RequestOptions().override(130, 195))
        .into(this)
}

fun RecyclerView.setupGenres(genresList: List<GenreModel>, model: MovieModel) {
    val genresAdapter = GenresAdapter()
    this.adapter = genresAdapter
    this.layoutManager = LinearLayoutManager(
        this.context,
        LinearLayoutManager.HORIZONTAL, false
    )

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
}