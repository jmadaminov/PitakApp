package com.badcompany.remote.model

import com.badcompany.core.Constants
import com.google.gson.annotations.SerializedName

data class UserCredentialsModel(@SerializedName("phoneNum") val phoneNum: String,
                                @SerializedName("password") val password: String,
                                @SerializedName("udId") val udId: String,
                                @SerializedName("userType") var userType: String = Constants.ROLE_DRIVER)

