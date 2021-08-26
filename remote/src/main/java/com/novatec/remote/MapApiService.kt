package com.novatec.remote

import com.novatec.core.Constants
import com.novatec.core.EAppType
import com.novatec.remote.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface MapApiService {

    @Streaming
    @GET("map/uzbekistan-latest-gh.zip")
    suspend fun downloadFile(): Response<ResponseBody>

}

