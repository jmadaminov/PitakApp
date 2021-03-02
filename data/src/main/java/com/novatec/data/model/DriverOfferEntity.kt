package com.novatec.data.model


data class DriverOfferEntity(val postId: Long,
                             val price: Int? = null,
                             val message: String? = null,
                             val repliedPostId: Long)

