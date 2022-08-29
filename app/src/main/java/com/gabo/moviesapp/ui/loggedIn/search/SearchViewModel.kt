package com.gabo.moviesapp.ui.loggedIn.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.paging.SearchMoviesPagingSource
import com.gabo.moviesapp.domain.useCases.SearchMoviesUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {
    fun getSearchedMovies(query: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(searchMoviesUseCase, query) }
        ).flow
            .cachedIn(viewModelScope)
    }
}