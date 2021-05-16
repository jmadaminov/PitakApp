package com.novatec.epitak.viewobjects

import android.os.Parcelable
import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType
import com.novatec.remote.model.Image
import com.novatec.remote.model.OfferDTO
import com.novatec.remote.model.ProfileDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferViewObj(val id: Long,
                        val postId: Long,
                        val offerType: EPostType,
                        val profileId: Long,
                        val profileViewObj: ProfileViewObj,
                        val status: EOfferStatus,
                        val visible: Boolean,
                        val submitDate: String,
                        val message: String?=null) : Parcelable {
    companion object {

        fun offerToViewObj(offerDTO: OfferDTO): OfferViewObj {
            val profileViewObj =
                ProfileViewObj(offerDTO.profile.phoneNum,
                               offerDTO.profile.name,
                               offerDTO.profile.surname,
                               offerDTO.profile.id,
                               ImageViewObj(offerDTO.profile.image?.id,
                                            offerDTO.profile.image?.link))
            return OfferViewObj(offerDTO.id,
                                offerDTO.postId,
                                offerDTO.offerType,
                                offerDTO.profileId,
                                profileViewObj,
                                offerDTO.status,
                                offerDTO.visible,
                                offerDTO.submitDate,
                                offerDTO.message)
        }

        fun offerToDTO(offerViewObj: OfferViewObj): OfferDTO {
            val profileDto =
                ProfileDTO(offerViewObj.profileViewObj.phoneNum,
                               offerViewObj.profileViewObj.name,
                               offerViewObj.profileViewObj.surname,
                               offerViewObj.profileViewObj.id,
                               Image(offerViewObj.profileViewObj.image?.id,
                                            offerViewObj.profileViewObj.image?.link))

            return OfferDTO(offerViewObj.id,
                            offerViewObj.postId,
                            offerViewObj.offerType,
                            offerViewObj.profileId,
                            profileDto,
                            offerViewObj.status,
                            offerViewObj.visible,
                            offerViewObj.submitDate,
                            offerViewObj.message)
        }
    }
}