package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.data.models.movieModels.MoviesModel
import com.gabo.moviesapp.domain.useCases.CheckIfMovieExistUseCase
import com.gabo.moviesapp.domain.usecases.GetMovieTrailerUseCase
import com.gabo.moviesapp.domain.usecases.GetSimilarMoviesUseCase
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val checkIfMovieExistUseCase: CheckIfMovieExistUseCase
) : ViewModel() {
    val movieExist = MutableStateFlow(false)

    fun checkIfMovieExist(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            movieExist.value = checkIfMovieExistUseCase(id)
        }
    }
}