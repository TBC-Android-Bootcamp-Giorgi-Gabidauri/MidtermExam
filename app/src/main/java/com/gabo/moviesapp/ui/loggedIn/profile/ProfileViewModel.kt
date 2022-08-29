package com.gabo.moviesapp.ui.loggedIn.profile

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.domain.useCases.GetMoviesUseCase
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    val db = FirebaseDatabase.getInstance().getReference("UserData")

    private val _movies: MutableStateFlow<List<MovieModel>> = MutableStateFlow(emptyList())
    val movies = _movies.asStateFlow()

    fun getMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            _movies.value = getMoviesUseCase(Unit)
        }
    }
}