package com.novatec.data.repository

import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.DriverOfferEntity
import com.novatec.data.model.FilterEntity
import com.novatec.data.model.PassengerPostEntity


interface PassengerPostDataStore {

    suspend fun filterPassengerPost(filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>>
    suspend fun offerARide(myOffer: DriverOfferEntity): ResponseWrapper<Any>
    suspend fun getPassengerPostById(id:Long): ResponseWrapper<PassengerPostEntity>

}