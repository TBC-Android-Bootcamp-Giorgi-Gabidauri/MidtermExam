package com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.paging.NowStreamingMoviesPagingSource
import com.gabo.moviesapp.domain.usecases.GetNowStreamingMoviesUseCase
import kotlinx.coroutines.flow.Flow

class SeeMoreNowStreamingViewModel(private val getNowPlayingMoviesUseCase: GetNowStreamingMoviesUseCase) :
    ViewModel() {
    fun getNowPlayingMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                NowStreamingMoviesPagingSource(getNowPlayingMoviesUseCase)
            }
        ).flow.cachedIn(viewModelScope)
    }
}