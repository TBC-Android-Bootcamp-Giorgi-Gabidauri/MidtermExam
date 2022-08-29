package com.gabo.moviesapp.di

import com.gabo.moviesapp.domain.useCases.*
import com.gabo.moviesapp.domain.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetGenresUseCase(get()) }
    factory { GetNowStreamingMoviesUseCase(get()) }
    factory { GetSimilarMoviesUseCase(get()) }
    factory { GetMovieTrailerUseCase(get()) }
    factory { SaveMovieUseCase(get()) }
    factory { DeleteMovieUseCase(get()) }
    factory { GetMoviesUseCase(get()) }
    factory { CheckIfMovieExistUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }
}