package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [HistoryPostRequest] fetched from the API
 */
data class HistoryPostRequest(@SerializedName("page") val page: Int = 1,
                              @SerializedName("size") val size: Int = 10)