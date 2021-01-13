package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [PassengerModel] fetched from the API
 */
data class PassengerModel(@SerializedName("id") var id: Long? = null,
                          @SerializedName("profileDTO") var profileDTO: ProfileDTO? = null,
                          @SerializedName("submitDate") var submitDate: String? = null)
