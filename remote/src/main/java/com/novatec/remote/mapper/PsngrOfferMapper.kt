//package com.novatec.remote.mapper
//
//import com.novatec.data.mapper.Mapper
//import com.novatec.data.model.ImageEntity
//import com.novatec.data.model.ProfileEntity
//import com.novatec.data.model.MyOfferEntity
//import com.novatec.remote.model.ImageDTO
//import com.novatec.remote.model.OfferDTO
//import com.novatec.remote.model.ProfileDTO
//import javax.inject.Inject
//
//open class PsngrOfferMapper @Inject constructor() : Mapper<MyOfferEntity, OfferDTO> {
//
//    override fun mapFromEntity(type: MyOfferEntity): OfferDTO {
//        val image = ImageDTO(type.profile?.image?.id, type.profile!!.image?.link)
//        val profile = ProfileDTO(type.profile!!.id, type.profile!!.phoneNum, type.profile!!.name,
//                                 type.profile!!.surname, image)
//        return OfferDTO(type.id!!,
//                        type.postId,
//                        type.offerType,
//                        type.profileId,
//                        profile,
//                        type.status,
//                        type.visible,
//                        type.submitDate,
//                        type.message,
//                        type.price,
//                        type.seat
//        )
//    }
//
//    override fun mapToEntity(type: OfferDTO): MyOfferEntity {
//        val image = ImageEntity(type.profile?.image?.id, type.profile!!.image?.link)
//        val profile = ProfileEntity(type.profile!!.id, type.profile!!.phoneNum, type.profile.name,
//                                    type.profile!!.surname, image)
//        return MyOfferEntity(type.id,
//                             type.postId,
//                             type.offerType,
//                             type.profileId,
//                             profile,
//                             type.status,
//                             type.visible,
//                             type.submitDate,
//                             type.message,
//                             type.price,
//                             type.seat
//        )
//    }
//
//}