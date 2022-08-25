package com.gabo.moviesapp.other.common

const val API_KEY = "938eec1132c5f17fa4284f5d8940768b"
const val BASE_URL = "https://api.themoviedb.org"
const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

object ApiEndpoints {
    const val POPULAR_MOVIES = "/3/movie/popular"
    const val SIMILAR_MOVIES = "/3/movie/{movie_id}/similar"
    const val SEARCH_MOVIES = "/3/search/movie"
    const val MOVIE_VIDEOS = "/3/movie/{movie_id}/videos"
    const val GENRES = "/3/genre/movie/list"
    const val NOW_PLAYING_MOVIES = "/3/movie/now_playing"
}