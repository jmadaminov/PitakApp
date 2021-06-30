package com.novatec.remote

import com.novatec.core.EAppType
import com.novatec.remote.model.*
import okhttp3.MultipartBody
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

    @Headers("Content-Type:application/json", "Accept: application/json")
    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("force_update/version")
    suspend fun getActiveAppVersions(@Query("appType") appType: String = EAppType.DRIVER.name,
                                     @Query("platformType") platformType: String = "ANDROID"): RespFormatter<List<IdVersionDTO>>

}

