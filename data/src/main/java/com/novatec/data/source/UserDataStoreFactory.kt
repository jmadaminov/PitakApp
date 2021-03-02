package com.novatec.data.source

import com.novatec.data.repository.UserDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class UserDataStoreFactory @Inject constructor(
//        private val userCache: BufferooCache,
//        private val userCacheDataStore: BufferooCacheDataStore,
        private val userRemoteDataStore: UserRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): UserDataStore {
//        if (isCached && !userCache.isExpired()) {
//            return retrieveCacheDataStore()
//        }
        return retrieveRemoteDataStore()
    }

//    /**
//     * Return an instance of the Cache Data Store
//     */
//    open fun retrieveCacheDataStore(): UserDataStore {
//        return userCacheDataStore
//    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): UserDataStore {
        return userRemoteDataStore
    }

}