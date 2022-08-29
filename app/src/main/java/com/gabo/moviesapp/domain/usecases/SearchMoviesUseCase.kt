package com.gabo.moviesapp.domain.useCases

import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase
import retrofit2.Response

class SearchMoviesUseCase(private val repository: Repository) :
    BaseUseCase<Pair<Int, String>, Response<MoviesModel>>() {
    override suspend fun invoke(params: Pair<Int, String>): Response<MoviesModel> {
        return repository.searchMovies(params.first, params.second)
    }
}