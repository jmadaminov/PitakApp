package com.novatec.data.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.DriverPostEntity
import com.novatec.data.model.OfferEntity


interface DriverPostDataStore {
    suspend fun createDriverPost(post: DriverPostEntity): ResultWrapper<DriverPostEntity?>
    suspend fun deleteDriverPost(identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(): ResultWrapper<List<DriverPostEntity>>
    suspend fun getHistoryDriverPosts(page: Int): ResultWrapper<List<DriverPostEntity>>

    suspend fun getDriverPostById(id: Long): ResponseWrapper<DriverPostEntity>
    suspend fun startTrip(id: Long): ResponseWrapper<String?>
    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>
    suspend fun removePassengerFromPost(postId: Long,
                                        passengerId: Long): ResponseWrapper<DriverPostEntity?>

    suspend fun removeParcelFromPost(postId: Long,
                                     parcelId: Long): ResponseWrapper<DriverPostEntity?>

    suspend fun getPassengerOffers(postId: Long): ResultWrapper<List<OfferEntity>>
    suspend fun getParcelOffers(postId: Long): ResultWrapper<List<OfferEntity>>
}