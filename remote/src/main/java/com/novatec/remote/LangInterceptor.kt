package com.novatec.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 */
class LangInterceptor(val lang: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Accept-Language", lang)
        return chain.proceed(requestBuilder.build())
    }
}