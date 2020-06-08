package com.badcompany.data.source

import com.badcompany.data.repository.FileUploadDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class FileUploadDataStoreFactory @Inject constructor(private val fileUploadRemoteDataStore: FileUploadRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): FileUploadDataStore {
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
    open fun retrieveRemoteDataStore(): FileUploadDataStore {
        return fileUploadRemoteDataStore
    }

}