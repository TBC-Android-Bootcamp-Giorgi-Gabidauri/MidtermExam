package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.domain.usecases.GetMovieTrailerUseCase
import com.gabo.moviesapp.domain.usecases.GetSimilarMoviesUseCase
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.flow.flow

class MovieDetailsViewModel(
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) : ViewModel() {

    fun getTrailers(movieId: Int) = flow<ResponseHandler<MovieTrailersModel>>{
        val trailers = getMovieTrailerUseCase(movieId)
        if (trailers.isSuccessful){
            emit(ResponseHandler.Success(trailers.body()))
        }else{
            emit(ResponseHandler.Error(trailers.errorBody()?.string()))
        }
    }
    fun getSimilarMovies(movieId: Int)= flow<ResponseHandler<MoviesModel>>{
        val response = getSimilarMoviesUseCase(movieId)
        if (response.isSuccessful){
            emit(ResponseHandler.Success(response.body()!!))
        }else{
            emit(ResponseHandler.Error(response.errorBody()?.string()))
        }
    }
}