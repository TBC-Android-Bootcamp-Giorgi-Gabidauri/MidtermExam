package com.gabo.moviesapp.data.providers.global

import com.gabo.moviesapp.data.models.genreModels.GenresModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.other.common.ApiEndpoints.GENRES
import com.gabo.moviesapp.other.common.ApiEndpoints.MOVIE_VIDEOS
import com.gabo.moviesapp.other.common.ApiEndpoints.NOW_PLAYING_MOVIES
import com.gabo.moviesapp.other.common.ApiEndpoints.POPULAR_MOVIES
import com.gabo.moviesapp.other.common.ApiEndpoints.SEARCH_MOVIES
import com.gabo.moviesapp.other.common.ApiEndpoints.SIMILAR_MOVIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET(POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviesModel>

    @GET(SEARCH_MOVIES)
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<MoviesModel>

    @GET(SIMILAR_MOVIES)
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int
    ): Response<MoviesModel>

    @GET(MOVIE_VIDEOS)
    suspend fun getMovieTrailer(@Path("movie_id") movieId: Int): Response<MovieTrailersModel>

    @GET(GENRES)
    suspend fun getGenres(): Response<GenresModel>

    @GET(NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MoviesModel>
}