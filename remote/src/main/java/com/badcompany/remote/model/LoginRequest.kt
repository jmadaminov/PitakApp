package com.badcompany.remote.model

import com.badcompany.core.Constants
import com.google.gson.annotations.SerializedName

/**
 * Representation for a [LoginRequest] fetched from the API
 */
data class LoginRequest(@SerializedName("phoneNum") val phoneNum: String,
                        @SerializedName("deviceId") val deviceId: String,
                        @SerializedName("userType") val userType: String = Constants.ROLE_DRIVER)