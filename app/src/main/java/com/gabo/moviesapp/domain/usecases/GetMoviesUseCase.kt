package com.gabo.moviesapp.domain.useCases

import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase

class GetMoviesUseCase(private val repository: Repository) : BaseUseCase<Unit, List<MovieModel>>() {
    override suspend fun invoke(params: Unit): List<MovieModel> {
        return repository.getMovies()
    }
}