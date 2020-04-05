package com.badcompany.data.repository

import com.badcompany.domain.ResultWrapper


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
    fun loginUser(): ResultWrapper<Exception, String>

}