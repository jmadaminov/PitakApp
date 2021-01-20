package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class AuthSuccessResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: UserInfoModel? = null)

data class UserInfoModel(@SerializedName("phoneNum") val phoneNum: String? = null,
                        @SerializedName("name")  val name: String? = null,
                        @SerializedName("surname")  val surname: String? = null,
                        @SerializedName("jwt")  val jwt: String? = null,
                         @SerializedName("rating")  val rating: Float = 0F,
                         @SerializedName("role")  val role: String? = null)
