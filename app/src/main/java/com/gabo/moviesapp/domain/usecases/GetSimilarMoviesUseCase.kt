package com.gabo.moviesapp.domain.usecases

import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.util.base.BaseUseCase
import retrofit2.Response

class GetSimilarMoviesUseCase(private val repository: Repository): BaseUseCase<Pair<Int,Int>,Response<MoviesModel>>() {
    override suspend fun invoke(params: Pair<Int,Int>): Response<MoviesModel> {
        return repository.getSimilarMovies(page = params.first, movieId = params.second)
    }
}