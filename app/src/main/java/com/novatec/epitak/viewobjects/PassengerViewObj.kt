package com.novatec.epitak.viewobjects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PassengerViewObj(var id: Long? = null,
                            var profile: ProfileViewObj? = null,
                            var submitDate: String? = null,
                            var offer: ArrangedOfferViewObj? = null
) : Parcelable

@Parcelize
data class ArrangedOfferViewObj(var message: String? = null,
                                var priceInt: Int? = null,
                                var seat: Int? = null,
                                var history: String? = null) : Parcelable

