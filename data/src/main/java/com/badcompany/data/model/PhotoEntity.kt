package com.badcompany.data.model

/**
 * Representation for a [PhotoEntity] fetched from an external layer data source
 */
data class PhotoEntity(val id: Long? = null,
                       val name: String? = null,
                       val type: String? = null,
                       val size: Long? = null,
                       val link: String? = null)