package com.gabo.moviesapp.di

import com.gabo.moviesapp.domain.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetGenresUseCase(get()) }
    factory { GetNowPlayingMoviesUseCase(get()) }
    factory { GetSimilarMoviesUseCase(get()) }
    factory { GetMovieTrailerUseCase(get()) }
}