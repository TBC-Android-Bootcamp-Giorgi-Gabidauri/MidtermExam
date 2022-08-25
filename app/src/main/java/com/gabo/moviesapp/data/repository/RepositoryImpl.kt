package com.gabo.moviesapp.data.repository

import com.gabo.moviesapp.data.models.genreModels.GenresModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.data.service.MoviesService
import retrofit2.Response

class RepositoryImpl(private val moviesService: MoviesService) : Repository {
    override suspend fun getPopularMovies(page: Int): Response<MoviesModel> {
        return moviesService.getPopularMovies(page)
    }

    override suspend fun getSimilarMovies(page: Int,movieId: Int): Response<MoviesModel> {
        return moviesService.getSimilarMovies(page,movieId)
    }

    override suspend fun searchMovies(page: Int, query: String): Response<MoviesModel> {
        return moviesService.searchMovies(page, query)
    }

    override suspend fun getMovieTrailer(movieId: Int): Response<MovieTrailersModel> {
        return moviesService.getMovieTrailer(movieId)
    }

    override suspend fun getGenres(): Response<GenresModel> {
        return moviesService.getGenres()
    }

    override suspend fun getNowPlayingMovies(page: Int): Response<MoviesModel> {
        return moviesService.getNowPlayingMovies(page)
    }
}