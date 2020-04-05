package com.badcompany.data.source

import com.badcompany.data.repository.UserDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class UserDataStoreFactory @Inject constructor(
//        private val bufferooCache: BufferooCache,
//        private val bufferooCacheDataStore: BufferooCacheDataStore,
        private val bufferooRemoteDataStore: UserRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): UserDataStore {
//        if (isCached && !bufferooCache.isExpired()) {
//            return retrieveCacheDataStore()
//        }
        return retrieveRemoteDataStore()
    }

//    /**
//     * Return an instance of the Cache Data Store
//     */
//    open fun retrieveCacheDataStore(): UserDataStore {
//        return bufferooCacheDataStore
//    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): UserDataStore {
        return bufferooRemoteDataStore
    }

}