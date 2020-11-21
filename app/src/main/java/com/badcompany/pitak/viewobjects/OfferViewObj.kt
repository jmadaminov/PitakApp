package com.badcompany.pitak.viewobjects

import android.os.Parcelable
import com.badcompany.core.EOfferStatus
import com.badcompany.core.EPostType
import com.badcompany.remote.model.OfferDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferViewObj(val id: Long,
                        val postId: Long,
                        val postType: EPostType,
                        val profileId: Long,
                        val status: EOfferStatus,
                        val visible: Boolean,
                        val submitDate: String,
                        val message: String) : Parcelable {
 companion object{

    fun offerToViewObj(offerDTO: OfferDTO) :OfferViewObj{
        return OfferViewObj(offerDTO.id,
                            offerDTO.postId,
                            offerDTO.postType,
                            offerDTO.profileId,
                            offerDTO.status,
                            offerDTO.visible,
                            offerDTO.submitDate,
                            offerDTO.message)
    }

    fun offerToDTO(offerViewObj: OfferViewObj) :OfferDTO{
        return OfferDTO(offerViewObj.id,
                            offerViewObj.postId,
                            offerViewObj.postType,
                            offerViewObj.profileId,
                            offerViewObj.status,
                            offerViewObj.visible,
                            offerViewObj.submitDate,
                            offerViewObj.message)
    }
 }
}