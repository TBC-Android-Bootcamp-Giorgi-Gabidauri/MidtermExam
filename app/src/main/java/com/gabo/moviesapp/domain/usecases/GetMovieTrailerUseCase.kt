package com.gabo.moviesapp.domain.usecases

import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase
import retrofit2.Response

class GetMovieTrailerUseCase(private val repository: Repository): BaseUseCase<Int,Response<MovieTrailersModel>>() {
    override suspend fun invoke(params: Int): Response<MovieTrailersModel> {
        return repository.getMovieTrailer(params)
    }
}