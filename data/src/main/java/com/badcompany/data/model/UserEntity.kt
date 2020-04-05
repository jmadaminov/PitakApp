package com.badcompany.data.model

/**
 * Representation for a [UserEntity] fetched from an external layer data source
 */
data class UserEntity( val phone:String,
                       val name:String,
                       val surname:String,
                       val isDriver : Boolean)