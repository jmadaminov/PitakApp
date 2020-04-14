package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.remote.model.UserCredentialsModel
import com.badcompany.remote.model.UserModel
import com.badcompany.remote.model.UserRegisterResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

    @GET("team.json")
    suspend fun getBufferoos(): Flowable<BufferooResponse>


    @POST("prof/auth")
    suspend fun userLogin(credentialsModel: UserCredentialsModel):  ResultWrapper<String>


    @POST("prof/reg")
    suspend fun userRegister(@Body user: UserModel): UserRegisterResponse

    class BufferooResponse {
        lateinit var team: List<UserModel>
    }

}

