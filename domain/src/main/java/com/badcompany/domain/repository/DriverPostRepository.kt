package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.PassengerPost

interface DriverPostRepository {

   suspend fun createDriverPost(token: String, post: DriverPost): ResultWrapper<String>


}