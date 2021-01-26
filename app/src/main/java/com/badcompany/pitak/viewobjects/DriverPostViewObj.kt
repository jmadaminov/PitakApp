package com.badcompany.pitak.viewobjects

import android.os.Parcelable
import com.badcompany.core.Constants
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverPostViewObj(val from: PlaceViewObj,
                             val to: PlaceViewObj,
                             val price: Int,
                             val departureDate: String,
                             val timeFirstPart: Boolean,
                             val timeSecondPart: Boolean,
                             val timeThirdPart: Boolean,
                             val timeFourthPart: Boolean,
                             val carId: Long? = null,
                             val car: CarViewObj? = null,
                             val remark: String? = null,
                             val seat: Int,
                             val postType: String = Constants.DRIVER_POST_SIMPLE) : Parcelable {
    companion object {
        fun fromDriverPost(post: DriverPost): DriverPostViewObj {
            return DriverPostViewObj(PlaceViewObj.fromPlace(post.from),
                                     PlaceViewObj.fromPlace(post.to),
                                     post.price,
                                     post.departureDate,
                                     post.timeFirstPart,
                                     post.timeSecondPart,
                                     post.timeThirdPart,
                                     post.timeFourthPart,
                                     null,
                                     null,
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
                        val name: String? = null) : Parcelable {
    companion object {
        fun fromPlace(place: Place): PlaceViewObj {
            return PlaceViewObj(place.districtId,
                                place.regionId,
                                place.lat,
                                place.lon,
                                place.regionName,
                                place.name)
        }
    }

}