package com.badcompany.remote

import com.badcompany.core.ResultWrapper
import com.badcompany.remote.model.UserCredentialsModel
import io.reactivex.Flowable
import com.badcompany.remote.model.UserModel
import retrofit2.http.GET
import retrofit2.http.POST
import java.lang.Exception

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {

    @GET("team.json")
    fun getBufferoos(): Flowable<BufferooResponse>


    @POST("prof/auth")
    fun userLogin(credentialsModel: UserCredentialsModel): ResultWrapper<Exception, String>

    class BufferooResponse {
        lateinit var team: List<UserModel>
    }

}

