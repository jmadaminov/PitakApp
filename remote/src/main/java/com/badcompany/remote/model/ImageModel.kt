package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

data class ImageModel(@SerializedName("id") var id: Long? = null,
                      @SerializedName("link") var link: String? = null)