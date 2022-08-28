package com.gabo.moviesapp.ui.loggedIn.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.paging.PopularMoviesPagingSource
import com.gabo.moviesapp.domain.useCases.CheckIfMovieExistUseCase
import com.gabo.moviesapp.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabo.moviesapp.domain.usecases.GetPopularMoviesUseCase
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val checkIfMovieExistUseCase: CheckIfMovieExistUseCase
) : ViewModel() {
    val movieExist = MutableStateFlow(false)

    fun getPopularMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(getPopularMoviesUseCase)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getNowPlayingMovies() = flow {
        val response = getNowPlayingMoviesUseCase(1)
        if (response.isSuccessful) {
            emit(ResponseHandler.Success(response.body()!!))
        } else {
            emit(ResponseHandler.Error(response.errorBody()?.string()))
        }
    }

    fun checkIfMovieExist(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            movieExist.value = checkIfMovieExistUseCase(id)
        }
    }
}