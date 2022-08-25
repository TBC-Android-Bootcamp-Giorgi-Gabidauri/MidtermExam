package com.gabo.moviesapp

import android.app.Application
import com.gabo.moviesapp.di.repositoryModule
import com.gabo.moviesapp.di.serviceModule
import com.gabo.moviesapp.di.useCasesModule
import com.gabo.moviesapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(provideKoinModules())
        }
    }

    private fun provideKoinModules(): List<Module> {
        return listOf(
            viewModelsModule,
            serviceModule,
            useCasesModule,
            repositoryModule
        )
    }
}