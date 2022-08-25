package com.gabo.moviesapp.domain.interceptor

import com.gabo.moviesapp.other.common.API_KEY
import okhttp3.Interceptor

class MoviesInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var req = chain.request()

        val url = req.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}