package com.novatec.remote.mapper

import com.novatec.data.mapper.Mapper
import com.novatec.data.model.ImageEntity
import com.novatec.data.model.OfferEntity
import com.novatec.data.model.ProfileEntity
import com.novatec.remote.model.ImageDTO
import com.novatec.remote.model.OfferDTO
import com.novatec.remote.model.ProfileDTO
import javax.inject.Inject

open class OfferMapper @Inject constructor() : Mapper<OfferEntity, OfferDTO> {

    override fun mapFromEntity(type: OfferEntity): OfferDTO {
        val profileImg = ImageDTO(type.profile!!.image?.id, type.profile!!.image?.link)
        val offerImg = ImageDTO(type.image?.id, type.image?.link)
        val profile = ProfileDTO(type.profile!!.id,
                                 type.profile!!.name,
                                 type.profile!!.surname,
                                 type.profile!!.id,
                                 profileImg)
        return OfferDTO(type.id,
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

    override fun mapToEntity(type: OfferDTO): OfferEntity {
        val image = ImageEntity(type.profile!!.image?.id, type.profile.image?.link)
        val offerImg = ImageEntity(type.image?.id, type.image?.link)
        val profile = ProfileEntity(type.profile.id,
                                    type.profile.name,
                                    type.profile.surname,
                                    type.profile.id,
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