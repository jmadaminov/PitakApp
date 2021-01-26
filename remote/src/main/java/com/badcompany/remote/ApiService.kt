package com.badcompany.remote

import com.badcompany.remote.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

//    @GET("team.json")
//    suspend fun getBufferoos(): Flowable<BufferooResponse>

    //Passenger POST API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("passenger_post/action/filter")
    suspend fun filterPassengerPost(
        @Body filter: FilterModel,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10): PassengerPostsResponse

    //AUTH API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/auth")
    suspend fun userLogin(@Body loginReq: LoginRequest): RespFormatter<UserCredentialsModel>

    @Headers("Content-Type:application/json")
    @POST("prof/mb/reg")
    suspend fun userRegister(@Body user: UserModel): AuthResponse



    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentialsModel): AuthSuccessResponse
    //END AUTH API

    ///CAR API



    //FILE UPLOAD API

    @Headers("Accept: application/json")
    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    //END FILE UPLOAD API


}

