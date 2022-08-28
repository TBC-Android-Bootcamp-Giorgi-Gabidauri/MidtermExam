package com.gabo.moviesapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabo.moviesapp.data.providers.local.LocalDataProvider
import com.gabo.moviesapp.data.providers.local.LocalDataProviderImpl
import com.gabo.moviesapp.data.providers.local.dao.MoviesDao
import com.gabo.moviesapp.data.providers.local.database.MoviesDatabase
import com.gabo.moviesapp.other.common.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val localStorageModule = module {
    single { provideDataBase(androidApplication()) }

    single { provideDao(get()) }

    single {
        LocalDataProviderImpl(
            get()
        )
    } bind LocalDataProvider::class
}

fun provideDataBase(application: Application): MoviesDatabase {
    return Room.databaseBuilder(application, MoviesDatabase::class.java, DATABASE_NAME)
        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideDao(dataBase: MoviesDatabase): MoviesDao {
    return dataBase.getMoviesDao
}