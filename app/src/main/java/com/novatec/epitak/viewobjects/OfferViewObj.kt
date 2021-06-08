package com.novatec.epitak.viewobjects

import android.os.Parcelable
import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType
import com.novatec.domain.domainmodel.Image
import com.novatec.domain.domainmodel.Offer
import com.novatec.domain.domainmodel.Profile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferViewObj(val id: Long,
                        val postId: Long,
                        val offerType: EPostType,
                        val profileId: Long,
                        val profileViewObj: ProfileViewObj,
                        val status: EOfferStatus,
                        val submitDate: String,
                        val message: String? = null) : Parcelable {
    companion object {

        fun offerToViewObj(offerDTO: Offer): OfferViewObj {
            val profileViewObj =
                ProfileViewObj(offerDTO.profile!!.phoneNum,
                               offerDTO.profile!!.name,
                               offerDTO.profile!!.surname,
                               offerDTO.profile!!.id,
                               ImageViewObj(offerDTO.profile!!.image?.id,
                                            offerDTO.profile!!.image?.link))
            return OfferViewObj(offerDTO.id!!,
                                offerDTO.postId!!,
                                offerDTO.offerType!!,
                                offerDTO.profileId!!,
                                profileViewObj,
                                offerDTO.status!!,
                                offerDTO.submitDate!!,
                                offerDTO.message!!)
        }

        fun offerToDTO(offerViewObj: OfferViewObj): Offer {
            val profileDto =
                Profile(offerViewObj.profileViewObj.phoneNum,
                        offerViewObj.profileViewObj.name,
                        offerViewObj.profileViewObj.surname,
                        offerViewObj.profileViewObj.id,
                        Image(offerViewObj.profileViewObj.image?.id,
                              offerViewObj.profileViewObj.image?.link))

            return Offer(offerViewObj.id,
                         offerViewObj.postId,
                         offerViewObj.offerType,
                         offerViewObj.profileId,
                         profileDto,
                         offerViewObj.status,
                         offerViewObj.submitDate,
                         offerViewObj.message)
        }
    }
}