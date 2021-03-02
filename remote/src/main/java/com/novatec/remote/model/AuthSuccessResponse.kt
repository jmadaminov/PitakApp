package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class AuthSuccessResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: UserInfoModel? = null)

data class UserInfoModel(@SerializedName("id") val id: String? = null,
                         @SerializedName("phoneNum") val phoneNum: String? = null,
                        @SerializedName("name")  val name: String? = null,
                        @SerializedName("surname")  val surname: String? = null,
                        @SerializedName("jwt")  val jwt: String? = null,
                         @SerializedName("rating")  val rating: Float = 0F,
                         @SerializedName("defCarId")  val defCarId: String? = null,
                         @SerializedName("role")  val role: String? = null)
