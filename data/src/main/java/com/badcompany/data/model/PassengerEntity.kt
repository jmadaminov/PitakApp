package com.badcompany.data.model

data class PassengerEntity( var id: Long? = null,
                            var profile: ProfileEntity? = null,
                            var submitDate: String? = null,
                            var offer: ArrangedOfferEntity? = null)

data class ArrangedOfferEntity(var message: String? = null,
                               var priceInt: Int? = null,
                               var seat: Int? = null,
                               var history: String? = null)