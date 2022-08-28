package com.gabo.moviesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.models.userModels.UserModel
import com.gabo.moviesapp.domain.useCases.DeleteMovieUseCase
import com.gabo.moviesapp.domain.useCases.SaveMovieUseCase
import com.gabo.moviesapp.domain.usecases.GetGenresUseCase
import com.gabo.moviesapp.other.responseHelpers.handleResponse
import com.gabo.moviesapp.ui.register.db
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {

    val movieDetailsFragmentArgs: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _userInfo = MutableStateFlow<UserModel?>(null)
    val userInfo = _userInfo.asStateFlow()

    fun getGenres() = flow {
        handleResponse(getGenresUseCase(Unit)).collect {
            emit(it)
        }
    }

    fun saveMovie(movie: MovieModel) {
        viewModelScope.launch {
            saveMovieUseCase(movie)
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            deleteMovieUseCase(id)
        }
    }

    init {
        getUserInfo()
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            FirebaseAuth.getInstance().currentUser?.uid?.let {
                db.child(FirebaseAuth.getInstance().currentUser?.uid!!).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userInfo = snapshot.getValue(UserModel::class.java)?: return
                        _userInfo.value = userInfo
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            }
        }
    }
}