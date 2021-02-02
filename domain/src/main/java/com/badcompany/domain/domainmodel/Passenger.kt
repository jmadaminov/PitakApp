package com.badcompany.domain.domainmodel

/**
 * Representation for a [Passenger] fetched from the API
 */

data class Passenger(var id: Long? = null,
                     var profile: Profile? = null,
                     var submitDate: String? = null,
                     var offer: ArrangedOffer? = null
)

data class ArrangedOffer(var message: String? = null,
                         var priceInt: Int? = null,
                         var seats: Int? = null,
                         var history: String? = null)
