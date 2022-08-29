package com.gabo.moviesapp.domain.useCases

import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.other.base.BaseUseCase

class DeleteMovieUseCase(private val repository: Repository) : BaseUseCase<Int, Unit>() {
    override suspend fun invoke(params: Int) {
        repository.deleteMovie(params)
    }
}