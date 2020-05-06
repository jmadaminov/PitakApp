package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

data class UserCredentialsModel(@SerializedName("phoneNum") var phoneNum: String,
                                var password: String)

