package com.novatec.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class PassengerPostsResponse(val code: Int? = null,
                                  val message: String? = null,
                                  val data: PassengerPostsPagination? = null)

data class PassengerPostsPagination(val pages: Int? = null,
                                  val elements: Int? = null,
                                  val data: List<PassengerPostModel>? = null)