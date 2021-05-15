package com.novatec.data.model

/**
 * Representation for a [AuthEntity] fetched from an external layer data source
 */
data class AuthEntity( val id: String? = null,
                       val phoneNum:String?=null,
                      val name:String?=null,
                      val jwt:String?=null,
                      val surname:String?=null,
                       val rating: Float = 0F,
                       val defCarId: String? = null,
                       val role : String?=null,
                       val image : ImageEntity?=null)