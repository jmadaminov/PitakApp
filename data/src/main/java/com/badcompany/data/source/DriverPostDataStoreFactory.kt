package com.badcompany.data.source

import com.badcompany.data.repository.DriverPostDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class DriverPostDataStoreFactory @Inject constructor(private val postDataStore: DriverPostDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): DriverPostDataStore {
//        if (isCached && !placeCache.isExpired()) {
//            return retrieveCacheDataStore()
//        }
        return retrieveRemoteDataStore()
    }

//    /**
//     * Return an instance of the Cache Data Store
//     */
//    open fun retrieveCacheDataStore(): PlaceDataStore {
//        return placeCacheDataStore
//    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): DriverPostDataStore {
        return postDataStore
    }

}