package com.gabo.moviesapp.data.providers.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.providers.local.converter.GenresTypeConverters
import com.gabo.moviesapp.data.providers.local.dao.MoviesDao

@Database(entities = [MovieModel::class], version = 3)
@TypeConverters(GenresTypeConverters::class)
abstract class  MoviesDatabase : RoomDatabase() {
    abstract val getMoviesDao: MoviesDao
}