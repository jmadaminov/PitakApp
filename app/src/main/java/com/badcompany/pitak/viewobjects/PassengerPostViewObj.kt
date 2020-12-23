package com.badcompany.pitak.viewobjects

import android.os.Parcelable
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.remote.model.PassengerPostModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengerPostViewObj(val id: Long,
                                val from: PlaceViewObj,
                                val to: PlaceViewObj,
                                val price: Int,
                                val departureDate: String,
                                val createdDate: String,
                                val updatedDate: String,
                                val finishedDate: String? = null,
                                val timeFirstPart: Boolean,
                                val timeSecondPart: Boolean,
                                val timeThirdPart: Boolean,
                                val timeFourthPart: Boolean,
                                val airConditioner: Boolean,
                                val remark: String,
                                val postStatus: String,
                                val seat: Int,
                                val postType: String) : Parcelable {


    companion object {
        fun mapFromPassengerPostModel(model: PassengerPost):PassengerPostViewObj {
          return  PassengerPostViewObj(
                model.id,
                PlaceViewObj(model.from.districtId,
                             model.from.regionId,
                             model.from.lat,
                             model.from.lon,
                             model.from.regionName,
                             model.from.name),
                PlaceViewObj(model.to.districtId,
                             model.to.regionId,
                             model.to.lat,
                             model.to.lon,
                             model.to.regionName,
                             model.to.name),
                model.price,
                model.departureDate,
                model.createdDate,
                model.updatedDate,
                model.finishedDate,
                model.timeFirstPart,
                model.timeSecondPart,
                model.timeThirdPart,
                model.timeFourthPart,
                model.airConditioner,
                model.remark,
                model.postStatus,
                model.seat,
                model.postType,
            )
        }
        fun mapFromPassengerPostModel(model: PassengerPostModel):PassengerPostViewObj {
          return  PassengerPostViewObj(
                model.id,
                PlaceViewObj(model.from.districtId,
                             model.from.regionId,
                             model.from.lat,
                             model.from.lon,
                             model.from.regionName,
                             model.from.name),
                PlaceViewObj(model.to.districtId,
                             model.to.regionId,
                             model.to.lat,
                             model.to.lon,
                             model.to.regionName,
                             model.to.name),
                model.price,
                model.departureDate,
                model.createdDate,
                model.updatedDate,
                model.finishedDate,
                model.timeFirstPart,
                model.timeSecondPart,
                model.timeThirdPart,
                model.timeFourthPart,
                model.airConditioner,
                model.remark,
                model.postStatus,
                model.seat,
                model.postType,
            )
        }
    }

}