package com.badcompany.domain.repository

import com.badcompany.domain.ResultWrapper
import com.badcompany.domain.domainmodel.PassengerPost

interface IPostRepository {

    fun getPassengerPosts(from: String, to: String): ResultWrapper<Exception, List<PassengerPost>>
    fun getDriverPosts(from: String, to: String): ResultWrapper<Exception, List<PassengerPost>>






}