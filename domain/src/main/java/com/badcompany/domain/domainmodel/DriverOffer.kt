package com.badcompany.domain.domainmodel

data class DriverOffer(val postId: Long,
                       val price: Int? = null,
                       val message: String? = null,
                       val repliedPostId: Long)