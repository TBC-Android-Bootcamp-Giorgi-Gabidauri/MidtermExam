package com.gabo.moviesapp.domain.usecases

import com.gabo.moviesapp.data.models.genreModels.GenresModel
import com.gabo.moviesapp.data.repository.Repository
import com.gabo.moviesapp.util.base.BaseUseCase
import retrofit2.Response

class GetGenresUseCase(private val repository: Repository): BaseUseCase<Unit,Response<GenresModel>>() {
    override suspend fun invoke(params: Unit): Response<GenresModel> {
        return repository.getGenres()
    }
}