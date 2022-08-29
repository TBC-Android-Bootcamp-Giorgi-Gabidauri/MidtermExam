package com.gabo.moviesapp.data.repository

import com.gabo.moviesapp.data.models.genreModels.GenresModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.data.providers.global.MoviesService
import com.gabo.moviesapp.data.providers.local.LocalDataProvider
import retrofit2.Response

class RepositoryImpl(
    private val moviesService: MoviesService,
    private val localDataProvider: LocalDataProvider
) : Repository {
    override suspend fun getPopularMovies(page: Int): Response<MoviesModel> {
        return moviesService.getPopularMovies(page)
    }

    override suspend fun getSimilarMovies(movieId: Int): Response<MoviesModel> {
        return moviesService.getSimilarMovies(movieId)
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

    override suspend fun saveMovie(movie: MovieModel) {
        localDataProvider.saveMovie(movie)
    }

    override suspend fun deleteMovie(id: Int) {
        localDataProvider.deleteMovie(id)
    }

    override suspend fun getMovies(): List<MovieModel> {
        return localDataProvider.getMovies()
    }

    override suspend fun movieExists(id: Int): Boolean {
        return localDataProvider.movieExists(id)
    }
}