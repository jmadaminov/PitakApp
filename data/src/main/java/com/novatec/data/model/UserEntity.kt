package com.novatec.data.model

/**
 * Representation for a [UserEntity] fetched from an external layer data source
 */
data class UserEntity(val phoneNum: String,
                      val name: String,
                      val surname: String,
//                      val role: String,
                      val deviceId: String)