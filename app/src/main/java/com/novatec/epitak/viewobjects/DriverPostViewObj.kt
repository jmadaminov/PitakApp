package com.novatec.epitak.viewobjects

import android.os.Parcelable
import com.novatec.core.Constants
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverPostViewObj(val id: Long? = null,
                             val from: PlaceViewObj,
                             val to: PlaceViewObj,
                             val price: Int,
                             val departureDate: String,
                             val timeFirstPart: Boolean,
                             val timeSecondPart: Boolean,
                             val timeThirdPart: Boolean,
                             val timeFourthPart: Boolean,
                             val car: CarViewObj? = null,
                             val remark: String? = null,
                             val seat: Int,
                             val postType: String = Constants.DRIVER_POST_SIMPLE) : Parcelable {
    companion object {
        fun fromDriverPost(post: DriverPost): DriverPostViewObj {
            val car = CarViewObj(post.car!!.id,
                                 IdNameViewObj(post.car!!.carModel!!.id,
                                               post.car!!.carModel!!.name),
                                 ImageViewObj(post.car!!.image!!.id, post.car!!.image!!.link),
                                 post.car!!.fuelType,
                                 CarColorViewObj(post.car!!.color!!.id,
                                                 post.car!!.color!!.hex,
                                                 post.car!!.color!!.name),
                                 post.car!!.carNumber,
                                 post.car!!.carYear,
                                 post.car!!.airConditioner!!,
                                 post.car!!.def!!
            )
            return DriverPostViewObj(post.id,
                                     PlaceViewObj.fromPlace(post.from),
                                     PlaceViewObj.fromPlace(post.to),
                                     post.price,
                                     post.departureDate,
                                     post.timeFirstPart,
                                     post.timeSecondPart,
                                     post.timeThirdPart,
                                     post.timeFourthPart,
                                     car,
                                     post.remark,
                                     post.seat,
                                     post.postType)
        }
    }
}

@Parcelize
data class PlaceViewObj(val districtId: Int? = null,
                        val regionId: Int? = null,
                        val lat: Double? = null,
                        val lon: Double? = null,
                        val regionName: String? = null,
                        val districtName: String? = null,
                        val name: String? = null) : Parcelable {



    companion object {
        fun fromPlace(place: Place): PlaceViewObj {
            return PlaceViewObj(place.districtId,
                                place.regionId,
                                place.lat,
                                place.lon,
                                place.regionName,
                                place.districtName,
                                place.name)
        }
    }

}