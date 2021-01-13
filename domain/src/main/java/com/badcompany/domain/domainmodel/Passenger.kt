package com.badcompany.domain.domainmodel

/**
 * Representation for a [Passenger] fetched from the API
 */

data class Passenger(var id: Long? = null,
                     var profile: Profile? = null,
                     var submitDate: String? = null)
