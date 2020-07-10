package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class PassengerPostsResponse(val code: Int? = null,
                                  val message: String? = null,
                                  val data: List<PassengerPostModel>? = null)