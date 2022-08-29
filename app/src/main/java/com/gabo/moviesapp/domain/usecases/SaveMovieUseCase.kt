package com.gabo.moviesapp.domain.useCases

import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase

class SaveMovieUseCase(private val repository: Repository) : BaseUseCase<MovieModel, Unit>() {
    override suspend fun invoke(params: MovieModel) {
        repository.saveMovie(params)
    }
}