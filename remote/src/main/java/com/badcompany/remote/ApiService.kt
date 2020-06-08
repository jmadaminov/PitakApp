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

    //AUTH API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/auth")
    suspend fun userLogin(@Body loginReq: LoginRequest): AuthResponse


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/reg")
    suspend fun userRegister(@Body user: UserModel): AuthResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentialsModel): AuthSuccessResponse
    //END AUTH API

    ///CAR API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("car/action")
    suspend fun createCar(/*@Header("Content-Language") lang: String,*/
        @Header("Authorization") token: String,
        @Body car: CarModel): CarModelsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("car/action/{identifier")
    suspend fun updateCar(/*@Header("Content-Language") lang: String,*/
        @Path(value = "identifier", encoded = true) identifier: String,
        @Header("Authorization") token: String,
        @Body car: CarModel): CarColorsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("car/action/{identifier}/def")
    suspend fun setDefaultCar(/*@Header("Content-Language") lang: String,*/
        @Header("Authorization") token: String,
        @Path(value = "identifier", encoded = true) identifier: String,
        @Body carDefault: CarDefaultBody = CarDefaultBody()): CarColorsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_model/action")
    suspend fun getCarModels(/*@Header("Content-Language") lang: String,*/ @Header("Authorization") token: String): CarModelsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_color/action")
    suspend fun getCarColors(/*@Header("Content-Language") lang: String,*/ @Header("Authorization") token: String): CarColorsResponse

    //END CAR API


    //FILE UPLOAD API

    @Headers("Accept: application/json")
    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    //END FILE UPLOAD API


//    class BufferooResponse {
//        lateinit var team: List<UserModel>
//    }

}

