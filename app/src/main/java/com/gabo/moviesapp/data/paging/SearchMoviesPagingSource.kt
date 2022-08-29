package com.gabo.moviesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.domain.useCases.SearchMoviesUseCase
import kotlinx.coroutines.delay

class SearchMoviesPagingSource(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val query: String
) : PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val page = params.key ?: 1
            if (page != 1) {
                delay(1500)
            }
            val response = searchMoviesUseCase(Pair(page,query))
            var movies: List<MovieModel> = emptyList()

            response.body()?.let {
                movies = it.movieResults
            }
            return LoadResult.Page(
                data = movies,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (page < response.body()!!.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}