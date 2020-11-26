package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [UserModel] fetched from the API
 */
data class UserModel(@SerializedName("phoneNum") val phoneNum:String,
             @SerializedName("name")   val name:String,
             @SerializedName("surname")   val surname:String,
//             @SerializedName("role ")   val role : String,
             @SerializedName("deviceId ")   val deviceId : String)