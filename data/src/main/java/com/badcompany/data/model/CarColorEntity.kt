package com.badcompany.data.model

/**
 * Representation for a [CarColorEntity] fetched from an external layer data source
 */
data class CarColorEntity(val id: Long,
                          val hex: String,
                          val name: String?=null)