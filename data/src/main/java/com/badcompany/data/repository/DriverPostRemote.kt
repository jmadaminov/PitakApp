package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity

interface DriverPostRemote {

    suspend fun createDriverPost(token: String, post: DriverPostEntity): ResultWrapper<String>


}