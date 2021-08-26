package com.novatec.data.repository

import com.novatec.core.ResultWrapper

interface MapRemote {

    suspend fun downloadmaps(targetDir: String): ResultWrapper<String>

}