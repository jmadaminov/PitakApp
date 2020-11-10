package com.badcompany.remote.model

import com.badcompany.core.Constants
import com.google.gson.annotations.SerializedName

data class UserCredentialsModel(@SerializedName("phoneNum") var phoneNum: String,
                                @SerializedName("password") var password: String,
                                @SerializedName("deviceId") var deviceId: String,
                                @SerializedName("userType") var userType: String = Constants.ROLE_DRIVER)

