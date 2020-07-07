package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.repository.DriverPostRemote
import com.badcompany.remote.mapper.DriverPostMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val postMapper: DriverPostMapper) :
    DriverPostRemote {

    override suspend fun createDriverPost(token: String,
                                          post: DriverPostEntity): ResultWrapper<String> {

        return try {
            val response = apiService.createPost(token, postMapper.mapFromEntity(post))
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun deleteDriverPost(token: String,
                                          identifier: String): ResultWrapper<String> {
        return try {
            val response = apiService.deletePost(token, identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishDriverPost(token: String,
                                          identifier: String): ResultWrapper<String> {
        return try {
            val response = apiService.finishPost(token, identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getActiveDriverPosts(token: String,
                                              lang: String): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = apiService.getActivePosts(token, lang)
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getHistoryDriverPosts(token: String,
                                               lang: String): ResultWrapper<List<DriverPostEntity>> {

        return try {
            val response = apiService.getHistoryPosts(token, lang)
            if (response.code == 1) {
                val posts = arrayListOf<DriverPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

//    override suspend fun getPlacesAutocomplete(token: String,
//                                               lang: String,
//                                               queryString: String): ResultWrapper<List<PlaceEntity>> {
//        return try {
//            val response = apiService.getPlacesFeed(token, lang, queryString)
//            if (response.code == 1) {
//                val places = arrayListOf<PlaceEntity>()
//                response.data!!.forEach {
//                    places.add(placeMapper.mapToEntity(it))
//                }
//                ResultWrapper.Success(places)
//            } else ErrorWrapper.ResponseError(response.code, response.message)
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }


}