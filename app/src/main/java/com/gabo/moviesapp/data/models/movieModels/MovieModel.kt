package com.gabo.moviesapp.data.models.movieModels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    @SerializedName("poster_path")
    val imageUrl: String?,
    @SerializedName("adult")
    val isAdultRated: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val ReleaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath:String,
    @SerializedName("original_language")
    val movieStatus: String,
    @SerializedName("vote_count")
    val movieVoteCount: Int,
    @SerializedName("vote_average")
    val Rating: Double
) : Parcelable
