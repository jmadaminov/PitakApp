package com.novatec.remote

import com.novatec.core.EAppType
import com.novatec.remote.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/auth")
    suspend fun userLogin(@Body loginReq: LoginRequest): RespFormatter<UserCredentialsModel>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/reg")
    suspend fun userRegister(@Body user: UserModel): AuthResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentialsModel): AuthSuccessResponse

    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("force_update/version")
    suspend fun getActiveAppVersions(@Query("appType") appType: String = EAppType.DRIVER.name,
                                     @Query("platformType") platformType: String = "ANDROID"): RespFormatter<List<IdVersionDTO>>

    @Streaming
    @GET("map/uzbekistan-latest-gh.zip")
    suspend fun downloadFile(): Response<ResponseBody>

}

