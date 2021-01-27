package com.badcompany.pitak.viewobjects

import android.os.Parcelable
import com.badcompany.core.EPostStatus
import com.badcompany.core.EPostType
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
                                val profileViewObj: ProfileViewObj,
                                val remark: String?=null,
                                val postStatus: EPostStatus,
                                val seat: Int,
                                val postType: EPostType) : Parcelable {


    companion object {
        fun mapFromPassengerPostModel(model: PassengerPost): PassengerPostViewObj {

            val profileImage = model.profile.image?.let {
                ImageViewObj(it.id, it.link)
            }

            val profileVObj = ProfileViewObj(model.profile.phoneNum,
                                             model.profile.name,
                                             model.profile.surname,
                                             model.profile.id,
                                             profileImage)


            return PassengerPostViewObj(
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
                profileVObj,
                model.remark,
                model.postStatus,
                model.seat,
                model.postType,
            )
        }

        fun mapFromPassengerPostModel(model: PassengerPostModel): PassengerPostViewObj {

            val profileImage = model.profile.image?.let {
                ImageViewObj(it.id, it.link)
            }

            val profileModel = ProfileViewObj(model.profile.phoneNum,
                                              model.profile.name,
                                              model.profile.surname,
                                              model.profile.id,
                                              profileImage)
            return PassengerPostViewObj(
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
                profileModel,
                model.remark,
                model.postStatus,
                model.seat,
                model.postType,
            )
        }
    }

}