package com.badcompany.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor(val token: String, val lang: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (token.isBlank()) throw Exception()
        requestBuilder.addHeader("Authorization", token)
        requestBuilder.addHeader("Accept-Language", lang)
        return chain.proceed(requestBuilder.build())
    }
}