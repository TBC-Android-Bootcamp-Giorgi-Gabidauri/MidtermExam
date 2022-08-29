package com.gabo.moviesapp.data.models.movieModels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gabo.moviesapp.other.common.TABLE_NAME
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class MovieModel(
    var isSaved: Boolean? = false,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("poster_path")
    var imageUrl: String?,
    @SerializedName("adult")
    val isAdultRated: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val ReleaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_language")
    val movieStatus: String,
    @SerializedName("vote_count")
    val movieVoteCount: Int,
    @SerializedName("vote_average")
    val Rating: Double
) : Parcelable