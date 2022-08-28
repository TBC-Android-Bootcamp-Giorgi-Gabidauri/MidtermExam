package com.gabo.moviesapp.data.providers.local

import com.gabo.moviesapp.data.models.movieModels.MovieModel

interface LocalDataProvider {
    suspend fun saveMovie(movie: MovieModel)
    suspend fun deleteMovie(id: Int)
    suspend fun getMovies(): List<MovieModel>
    suspend fun movieExists(id: Int): Boolean
}