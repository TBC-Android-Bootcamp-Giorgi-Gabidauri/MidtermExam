package com.gabo.moviesapp.di

import com.gabo.moviesapp.data.providers.global.MoviesService
import com.gabo.moviesapp.domain.interceptor.MoviesInterceptor
import com.gabo.moviesapp.other.common.BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule = module {
    single { MoviesInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideMoviesService(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: MoviesInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideMoviesService(retrofit: Retrofit): MoviesService =
    retrofit.create(MoviesService::class.java)
