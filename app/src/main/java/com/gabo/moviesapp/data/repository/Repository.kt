package com.gabo.moviesapp.data.repository

import com.gabo.moviesapp.data.models.genreModels.GenresModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import retrofit2.Response

interface Repository {
    suspend fun getPopularMovies(page: Int): Response<MoviesModel>
    suspend fun getSimilarMovies(movieId: Int): Response<MoviesModel>
    suspend fun searchMovies(page: Int, query: String): Response<MoviesModel>
    suspend fun getMovieTrailer(movieId: Int): Response<MovieTrailersModel>
    suspend fun getGenres(): Response<GenresModel>
    suspend fun getNowPlayingMovies(page: Int): Response<MoviesModel>
    suspend fun saveMovie(movie: MovieModel)
    suspend fun deleteMovie(id: Int)
    suspend fun getMovies(): List<MovieModel>
    suspend fun movieExists(id: Int): Boolean
}