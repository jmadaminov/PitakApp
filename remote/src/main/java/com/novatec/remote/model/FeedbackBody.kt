package com.novatec.remote.model

import com.novatec.core.Constants
import com.google.gson.annotations.SerializedName

data class FeedbackBody(@SerializedName("content") var content: String? = null,
                        @SerializedName("type") var type: String = Constants.ROLE_DRIVER)

