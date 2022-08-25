package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.domain.usecases.GetMovieTrailerUseCase
import com.gabo.moviesapp.domain.usecases.GetSimilarMoviesUseCase
import com.gabo.moviesapp.util.responseHelpers.ResponseHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) : ViewModel() {


    private var _similarMovies: MutableStateFlow<MoviesModel>? = null
    val similarMovies: StateFlow<MoviesModel>?
        get() {
            return _similarMovies?.asStateFlow()
        }

    private var _movieVideos: MutableStateFlow<MovieTrailersModel>? = null
    val movieVideos: StateFlow<MovieTrailersModel>?
        get() {
            return _movieVideos?.asStateFlow()
        }

//    fun getSimilarMovies(id: Int) {
//        viewModelScope.launch {
//            try {
//                val movies = getSimilarMoviesUseCase(id)
//                withContext(Dispatchers.Main) {
//                    movies.body()?.let {
//                        _similarMovies?.value = it
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d("onError", e.message.toString())
//            }
//        }
//    }
    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            try {
                val videos = getMovieTrailerUseCase(movieId)
                _movieVideos?.value = videos.body()!!
            } catch (e: Exception) {}
        }
    }

    fun getTrailers(movieId: Int) = flow<ResponseHandler<MovieTrailersModel>>{
        val trailers = getMovieTrailerUseCase(movieId)
        if (trailers.isSuccessful){
            emit(ResponseHandler.Success(trailers.body()))
        }else{
            emit(ResponseHandler.Error(trailers.errorBody()?.string()))
        }
    }
//    val movieTrailer : MutableStateFlow<MovieTrailersModel>? = null
//


//    fun getSimilarMovies(movieId: Int) : Flow<PagingData<MovieModel>>{
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20
//            ),
//            pagingSourceFactory = {
//                SimilarMoviesPagingSource(getSimilarMoviesUseCase,movieId)
//            }
//        ).flow.cachedIn(viewModelScope)
//    }

    fun getSimilarMovies(movieId: Int)= flow<ResponseHandler<MoviesModel>>{
        val response = getSimilarMoviesUseCase(Pair(1,movieId))
        if (response.isSuccessful){
            emit(ResponseHandler.Success(response.body()!!))
        }else{
            emit(ResponseHandler.Error(response.errorBody()?.string()))
        }
    }

//    fun getMovieVideos(movieId: Int) {
//        viewModelScope.launch {
//            try {
//                val videos = getMovieTrailerUseCase(movieId)
//                movieTrailer?.value = videos.body()!!
//            } catch (e: Exception) {
//            }
//        }
//    }
}