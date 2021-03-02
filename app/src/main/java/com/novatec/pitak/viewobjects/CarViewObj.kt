package com.novatec.pitak.viewobjects

import android.os.Parcelable
import com.novatec.domain.domainmodel.CarDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarViewObj(var id: Long? = null,
                      var carModel: IdNameViewObj? = null,
                      var image: ImageViewObj? = null,
                      var fuelType: String? = null,
                      var carColor: CarColorViewObj? = null,
                      var carNumber: String? = null,
                      var carYear: Int? = null,
                      var airConditioner: Boolean = false,
                      var def: Boolean = false,
                      var imageList: List<ImageViewObj> = arrayListOf()) : Parcelable {

    companion object {
        fun fromCarModel(carDetails: CarDetails): CarViewObj {
            val imgList = arrayListOf<ImageViewObj>()
            carDetails.imageList?.forEach {
                imgList.add(ImageViewObj(it.id, it.link))
            }
            return CarViewObj(carDetails.id,
                              IdNameViewObj(carDetails.carModel!!.id),
                              ImageViewObj(carDetails.image!!.id,
                                           carDetails.image!!.link),
                              carDetails.fuelType,
                              CarColorViewObj(carDetails.carColor!!.id),
                              carDetails.carNumber,
                              carDetails.carYear,
                              carDetails.airConditioner,
                              carDetails.def,
                              imgList)
        }
    }
}

@Parcelize
data class IdNameViewObj(var id: Long? = null,
                         var name: String? = null) : Parcelable

@Parcelize
data class ImageViewObj(var id: Long? = null,
                        var link: String? = null) : Parcelable

@Parcelize
data class CarColorViewObj(val id: Long? = null,
                           val hex: String? = null,
                           val name: String? = null) : Parcelable