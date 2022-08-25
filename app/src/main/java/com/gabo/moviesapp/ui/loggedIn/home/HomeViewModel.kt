package com.gabo.moviesapp.ui.loggedIn.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.paging.NowPlayingMoviesPagingSource
import com.gabo.moviesapp.data.paging.PopularMoviesPagingSource
import com.gabo.moviesapp.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabo.moviesapp.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(getPopularMoviesUseCase)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getNowPlayingMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                NowPlayingMoviesPagingSource(getNowPlayingMoviesUseCase)
            }
        ).flow.cachedIn(viewModelScope)
    }
}