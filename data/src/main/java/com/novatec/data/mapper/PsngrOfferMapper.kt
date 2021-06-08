//package com.novatec.data.mapper
//
//import com.novatec.data.model.UserOfferEntity
//import com.novatec.domain.domainmodel.Offer
//import javax.inject.Inject
//
//open class PsngrOfferMapper @Inject constructor() : Mapper<UserOfferEntity, Offer> {
//
//    override fun mapFromEntity(type: UserOfferEntity): Offer {
//        return Offer(type.id,
//                     type.postId,
//                     null,
//                     type.status,
//                     type.message,
//                     type.submitDate,
//                     type.price,
//                     type.seat
//        )
//    }
//
//    override fun mapToEntity(type: Offer): UserOfferEntity {
//        return UserOfferEntity(
//            type.id,
//            type.postId,
//            null,
//            null,
//            null,
//            type.status,
//            null,
//            type.submitDate,
//            type.message,
//            type.priceInt,
//            type.seat,
////                               type.status,
////                               type.message,
////                               type.submitDate,
////                               type.price,
////                               type.seat
//        )
//    }
//
//}