package com.novatec.data.model

import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType

data class OfferEntity(val id: Long? = null,
                       val postId: Long? = null,
                       val offerType: EPostType? = null,
                       val profileId: Long? = null,
                       val profile: ProfileEntity? = null,
                       val status: EOfferStatus? = null,
                       val submitDate: String? = null,
                       val message: String? = null,
                       val image: ImageEntity? = null,
                       val price: Int? = null,
                       val seat: Int? = null)