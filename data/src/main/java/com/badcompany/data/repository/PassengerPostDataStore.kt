package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.FilterEntity
import com.badcompany.data.model.PassengerPostEntity


interface PassengerPostDataStore {

    suspend fun filterPassengerPost(

                                    filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>>

}