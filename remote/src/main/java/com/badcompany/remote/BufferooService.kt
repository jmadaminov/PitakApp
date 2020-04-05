package org.buffer.android.boilerplate.remote

import io.reactivex.Flowable
import com.badcompany.remote.model.UserModel
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {

    @GET("team.json")
    fun getBufferoos(): Flowable<BufferooResponse>

    class BufferooResponse {
        lateinit var team: List<UserModel>
    }

}