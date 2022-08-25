package com.gabo.moviesapp.di

import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.HomeViewModel
import com.gabo.moviesapp.ui.logIn.LogInViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsViewModel
import com.gabo.moviesapp.ui.register.RegisterViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    factory { HomeViewModel(get(),get()) }
    factory { LogInViewModel() }
    factory { RegisterViewModel() }
    factory { MovieDetailsViewModel(get(),get()) }
    factory { MainViewModel(get()) }
}