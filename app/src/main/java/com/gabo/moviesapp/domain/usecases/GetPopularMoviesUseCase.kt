package com.gabo.moviesapp.domain.usecases

import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.util.base.BaseUseCase
import retrofit2.Response

class GetPopularMoviesUseCase(private val repository: Repository) :
    BaseUseCase<Int, Response<MoviesModel>>() {
    override suspend fun invoke(params: Int): Response<MoviesModel> {
        return repository.getPopularMovies(params)
    }
}