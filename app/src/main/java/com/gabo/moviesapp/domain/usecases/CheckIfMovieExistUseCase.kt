package com.gabo.moviesapp.domain.useCases

import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase

class CheckIfMovieExistUseCase(private val repository: Repository) : BaseUseCase<Int,Boolean>(){
    override suspend fun invoke(params: Int): Boolean {
        return repository.movieExists(params)
    }
}