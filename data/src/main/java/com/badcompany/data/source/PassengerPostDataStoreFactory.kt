package com.badcompany.data.source

import com.badcompany.data.repository.PassengerPostDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class PassengerPostDataStoreFactory @Inject constructor(private val postDataStore: PassengerPostDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): PassengerPostDataStore {
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
    open fun retrieveRemoteDataStore(): PassengerPostDataStore {
        return postDataStore
    }

}