package com.gabo.moviesapp.di

import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.HomeViewModel
import com.gabo.moviesapp.ui.logIn.LogInViewModel
import com.gabo.moviesapp.ui.loggedIn.favourites.FavouritesViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.similarMovies.SimilarMoviesViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.trailers.TrailersViewModel
import com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming.SeeMoreNowStreamingViewModel
import com.gabo.moviesapp.ui.loggedIn.profile.ProfileViewModel
import com.gabo.moviesapp.ui.loggedIn.search.SearchViewModel
import com.gabo.moviesapp.ui.register.RegisterViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    factory { HomeViewModel(get(),get()) }
    factory { LogInViewModel() }
    factory { RegisterViewModel() }
    factory { MovieDetailsViewModel() }
    factory { MainViewModel(get()) }
    factory { SeeMoreNowStreamingViewModel(get()) }
    factory { ProfileViewModel() }
    factory { FavouritesViewModel() }
    factory { SearchViewModel() }
    factory { TrailersViewModel(get()) }
    factory { SimilarMoviesViewModel(get()) }
}