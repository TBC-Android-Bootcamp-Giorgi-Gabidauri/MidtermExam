package com.gabo.moviesapp.ui

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.domain.usecases.GetGenresUseCase
import com.gabo.moviesapp.other.responseHelpers.handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class MainViewModel(private val getGenresUseCase: GetGenresUseCase) : ViewModel() {
    val movieDetailsFragmentArgs: MutableStateFlow<Int> = MutableStateFlow(0)

    fun getGenres() = flow {
        handleResponse(getGenresUseCase(Unit)).collect {
            emit(it)
        }
    }
}