package com.novatec.domain.domainmodel

import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType

data class Offer(val id: Long? = null,
                 val postId: Long? = null,
                 val offerType: EPostType? = null,
                 val profileId: Long? = null,
                 val profile: Profile? = null,
                 val status: EOfferStatus? = null,
                 val submitDate: String? = null,
                 val message: String? = null,
                 val image: Image? = null,
                 val price: Int? = null,
                 val seat: Int? = null)