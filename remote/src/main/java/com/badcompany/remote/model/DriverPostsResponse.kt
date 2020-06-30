package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class DriverPostsResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: List<DriverPostModel>? = null)