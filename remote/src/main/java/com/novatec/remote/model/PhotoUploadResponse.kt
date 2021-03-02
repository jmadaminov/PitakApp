package com.novatec.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class PhotoUploadResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: PhotoUploadModel? = null)

data class PhotoUploadModel(val id: Long? = null,
                            val name: String? = null,
                            val type: String? = null,
                            val size: Long? = null,
                            val link: String? = null)
