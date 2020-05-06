package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [UserModel] fetched from the API
 */
data class UserModel(@SerializedName("phoneNum") val phoneNum:String,
                val name:String,
                val surname:String,
                val role : String)