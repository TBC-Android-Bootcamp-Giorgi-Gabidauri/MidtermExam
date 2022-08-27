package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.trailers

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.domain.usecases.GetMovieTrailerUseCase
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.flow.flow

class TrailersViewModel(
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase
) : ViewModel() {
    fun getTrailers(movieId: Int) = flow {
        val trailers = getMovieTrailerUseCase(movieId)
        if (trailers.isSuccessful) {
            emit(ResponseHandler.Success(trailers.body()))
        } else {
            emit(ResponseHandler.Error(trailers.errorBody()?.string()))
        }
    }
}