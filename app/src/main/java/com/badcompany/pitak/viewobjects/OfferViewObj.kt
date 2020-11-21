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
        return OfferViewObj(offerDTO.profileId,
                            offerDTO.postId,
                            offerDTO.postType,
                            offerDTO.profileId,
                            offerDTO.status,
                            offerDTO.visible,
                            offerDTO.submitDate,
                            offerDTO.message)
    }
 }
}