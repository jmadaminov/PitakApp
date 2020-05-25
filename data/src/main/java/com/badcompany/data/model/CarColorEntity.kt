package com.badcompany.data.model

/**
 * Representation for a [CarColorEntity] fetched from an external layer data source
 */
data class CarColorEntity(val id: Int,
                          val hex: Int,
                          val nameEn: String,
                          val nameUz: String,
                          val nameRu: String)