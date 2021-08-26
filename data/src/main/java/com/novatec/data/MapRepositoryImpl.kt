package com.novatec.data

import com.novatec.core.ResultWrapper
import com.novatec.data.repository.MapRemote
import com.novatec.domain.repository.MapRepository
import com.novatec.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class MapRepositoryImpl @Inject constructor(private val mapRemote: MapRemote) :
    MapRepository {

    override suspend fun downloadmaps(targetDir: String): ResultWrapper<String> {
        return mapRemote.downloadmaps(targetDir)
    }

}