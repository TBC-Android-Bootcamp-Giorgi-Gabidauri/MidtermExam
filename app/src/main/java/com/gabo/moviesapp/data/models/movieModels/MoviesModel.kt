package com.gabo.moviesapp.data.models.movieModels

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    var page: Int,
    @SerializedName("results")
    var movieResults: List<MovieModel>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)