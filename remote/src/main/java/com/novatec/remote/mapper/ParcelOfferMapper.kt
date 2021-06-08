//package com.novatec.remote.mapper
//
//import com.novatec.data.mapper.Mapper
//import com.novatec.data.model.ParcelOfferEntity
//import com.novatec.remote.model.ParcelModel
//import javax.inject.Inject
//
//open class ParcelOfferMapper @Inject constructor() : Mapper<ParcelOfferEntity, ParcelModel> {
//
//    override fun mapFromEntity(type: ParcelOfferEntity): ParcelModel {
//        return ParcelModel(type.id/*,
//                           type.postId,
//                           type.repliedPostId,
//                           type.status,
//                           type.message,
//                           type.submitDate,
//                           type.priceInt,
//                           type.seat*/
//        )
//    }
//
//    override fun mapToEntity(type: ParcelModel): ParcelOfferEntity {
//        return ParcelOfferEntity(type.id,
//                                 0,
//                                 0,
//                                 "",
//                                 "",
//                                 "",
//                                 0,
//                                 0
//
//            /*,
//                                 type.postId,
//                                 type.repliedPostId,
//                                 type.status,
//                                 type.message,
//                                 type.submitDate,
//                                 type.priceInt,
//                                 type.seat*/
//        )
//    }
//
//}