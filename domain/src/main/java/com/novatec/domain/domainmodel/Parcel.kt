package com.novatec.domain.domainmodel

data class Parcel(val id: Long,
                  val profile: Profile,
                  val submitDate: String,
                  val offer: Offer,
                  val status: String)
