package com.gabo.moviesapp.data.providers.local

import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.providers.local.database.MoviesDatabase

class LocalDataProviderImpl(private val database: MoviesDatabase) : LocalDataProvider {
    override suspend fun saveMovie(movie: MovieModel) {
        database.getMoviesDao.saveMovie(movie)
    }

    override suspend fun deleteMovie(id: Int) {
        database.getMoviesDao.deleteMovie(id)
    }

    override suspend fun getMovies(): List<MovieModel> {
        return database.getMoviesDao.getMovies()
    }

    override suspend fun movieExists(id: Int): Boolean {
        return database.getMoviesDao.movieExists(id)
    }
}