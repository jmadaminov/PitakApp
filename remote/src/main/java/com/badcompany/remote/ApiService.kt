package com.badcompany.remote

import com.badcompany.remote.model.*
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("prof/mb/auth")
    suspend fun userLogin(@Body loginReq: LoginRequest): RespFormatter<UserCredentialsModel>

    @POST("prof/mb/reg")
    suspend fun userRegister(@Body user: UserModel): AuthResponse

    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentialsModel): AuthSuccessResponse

    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

}

