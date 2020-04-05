package com.badcompany.remote.model

/**
 * Representation for a [UserModel] fetched from the API
 */
class UserModel( val phone:String,
                 val name:String,
                 val surname:String,
                 val isDriver : Boolean)