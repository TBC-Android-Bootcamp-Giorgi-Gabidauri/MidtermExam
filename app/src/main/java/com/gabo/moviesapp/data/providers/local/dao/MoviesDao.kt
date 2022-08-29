package com.gabo.moviesapp.data.providers.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.other.common.TABLE_NAME

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieModel)

    @Query("DELETE FROM $TABLE_NAME WHERE id=:id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getMovies(): List<MovieModel>

    @Query("SELECT EXISTS(SELECT*FROM $TABLE_NAME WHERE id=:id)")
    suspend fun movieExists(id: Int): Boolean
}