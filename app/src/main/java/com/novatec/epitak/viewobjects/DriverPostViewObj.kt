package com.novatec.epitak.viewobjects

import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.novatec.core.EPostStatus
import com.novatec.core.EPostType
import com.novatec.domain.domainmodel.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverPostViewObj(var id: Long,
                             var from: PlaceViewObj? = null,
                             var to: PlaceViewObj? = null,
                             var price: Int? = null,
                             var departureDate: String? = null,
                             var finishedDate: String? = null,
                             var timeFirstPart: Boolean? = null,
                             var timeSecondPart: Boolean? = null,
                             var timeThirdPart: Boolean? = null,
                             var timeFourthPart: Boolean? = null,
                             var car: CarViewObj? = null,
                             var remark: String? = null,
                             var seat: Int? = null,
                             var offerCount: Int? = null,
                             var passengerCount: Int? = null,
                             var availableSeats: Int? = null,
                             var postStatus: EPostStatus? = null,
                             var pkg: Boolean? = null,
                             var parcelCount: Int? = null,
                             var passengerList: List<PassengerViewObj> = arrayListOf(),
                             var parcelList: List<ParcelViewObj> = arrayListOf(),
                             var postType: EPostType) : Parcelable, BaseObservable() {


    fun toDriverPost(): DriverPost {
        val carModel = car?.carModel?.let { CarModelBody(it.id!!, it.name) }

        val car = CarInPost(car!!.id,
                            car!!.carModel!!.id,
                            Image(car!!.image!!.id,
                                  car!!.image!!.link),
                            carModel,
                            car!!.fuelType,
                            CarColorBody(car!!.carColor!!.id,
                                         car!!.carColor!!.hex,
                                         car!!.carColor!!.name),
                            car!!.carNumber,
                            car!!.carYear,
                            car!!.airConditioner,
                            car!!.def
        )
        return DriverPost(id,
                          PlaceViewObj.toPlace(from!!),
                          PlaceViewObj.toPlace(to!!),
                          price!!,
                          departureDate!!,
                          finishedDate,
                          timeFirstPart!!,
                          timeSecondPart!!,
                          timeThirdPart!!,
                          timeFourthPart!!,
                          car.id,
                          car,
                          remark,
                          seat!!,
                          offerCount,
                          passengerCount,
                          availableSeats,
                          postStatus,
                          pkg,
                          parcelCount,
                          listOf(),
                          listOf(),
                          postType)
    }

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
                                     post.finishedDate,
                                     post.timeFirstPart,
                                     post.timeSecondPart,
                                     post.timeThirdPart,
                                     post.timeFourthPart,
                                     car,
                                     post.remark,
                                     post.seat,
                                     post.passengerOfferCount,
                                     post.passengerCount,
                                     post.availableSeats,
                                     post.postStatus,
                                     post.pkg,
                                     post.parcelOfferCount,
                                     arrayListOf(),
                                     arrayListOf(),
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

        fun toPlace(place: PlaceViewObj): Place {
            return Place(place.districtId,
                         place.regionId,
                         place.lat,
                         place.lon,
                         place.regionName,
                         place.districtName,
                         place.name)
        }

    }

    fun toPlace(): Place {
        return Place(districtId,
                     regionId,
                     lat,
                     lon,
                     regionName,
                     districtName,
                     name)
    }

}