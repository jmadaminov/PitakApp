package com.novatec.data.mapper

import com.novatec.data.model.ImageEntity
import com.novatec.data.model.OfferEntity
import com.novatec.data.model.ProfileEntity
import com.novatec.domain.domainmodel.Image
import com.novatec.domain.domainmodel.Offer
import com.novatec.domain.domainmodel.Profile
import javax.inject.Inject

open class OfferMapper @Inject constructor() : Mapper<OfferEntity, Offer> {

    override fun mapFromEntity(type: OfferEntity): Offer {
        val profileImg = Image(type.profile!!.image?.id, type.profile.image?.link)
        val offerImg = Image(type.image?.id, type.image?.link)
        val profile = Profile(type.profile.phoneNum,
                              type.profile.name,
                              type.profile.surname,
                              type.profile.id,
                              profileImg)
        return Offer(type.id,
                     type.postId,
                     type.offerType,
                     type.profileId,
                     profile,
                     type.status,
                     type.submitDate,
                     type.message,
                     offerImg,
                     type.price,
                     type.seat
        )
    }

    override fun mapToEntity(type: Offer): OfferEntity {
        val image = ImageEntity(type.profile!!.image?.id, type.profile!!.image?.link)
        val offerImg = ImageEntity(type.image?.id, type.image?.link)
        val profile = ProfileEntity(type.profile!!.phoneNum,
                                    type.profile!!.name,
                                    type.profile!!.surname,
                                    type.profile!!.id,
                                    image)
        return OfferEntity(type.id,
                           type.postId,
                           type.offerType,
                           type.profileId,
                           profile,
                           type.status,
                           type.submitDate,
                           type.message,
                           offerImg,
                           type.price,
                           type.seat
        )
    }

}