package com.badcompany.data.model

/**
 * Representation for a [AuthEntity] fetched from an external layer data source
 */
data class AuthEntity( val phoneNum:String?=null,
                      val name:String?=null,
                      val jwt:String?=null,
                      val surname:String?=null,
                      val role : String?=null)