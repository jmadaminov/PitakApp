package com.badcompany.domain.domainmodel

data class User (
    val phone:String,
    val name:String,
    val surname:String,
//    val username:String,
//    val password:String,
    val isDriver : Boolean
)