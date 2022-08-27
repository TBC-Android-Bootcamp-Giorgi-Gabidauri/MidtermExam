package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.similarMovies

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.domain.usecases.GetSimilarMoviesUseCase
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.flow.flow

class SimilarMoviesViewModel(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) : ViewModel() {
    fun getSimilarMovies(movieId: Int) = flow<ResponseHandler<MoviesModel>> {
        val response = getSimilarMoviesUseCase(movieId)
        if (response.isSuccessful) {
            emit(ResponseHandler.Success(response.body()!!))
        } else {
            emit(ResponseHandler.Error(response.errorBody()?.string()))
        }
    }
}