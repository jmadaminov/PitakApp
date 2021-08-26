package com.novatec.domain.repository

import com.novatec.core.ResultWrapper

interface MapRepository {

    suspend fun downloadmaps(targetDir: String): ResultWrapper<String>

}