package com.gabo.moviesapp.di

import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.data.repository.RepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        RepositoryImpl(get())
    } bind Repository::class
}