package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.PassengerPost

interface DriverPostRepository {

   suspend fun createDriverPost(token: String, post: DriverPost): ResultWrapper<String>
   suspend fun deleteDriverPost(token: String, identifier: String): ResultWrapper<String>
   suspend fun finishDriverPost(token: String, identifier: String): ResultWrapper<String>
   suspend fun getActiveDriverPosts(token: String, lang: String): ResultWrapper<List<DriverPost>>
   suspend fun getHistoryDriverPosts(token: String, lang: String): ResultWrapper<List<DriverPost>>

}