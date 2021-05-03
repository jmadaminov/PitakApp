package com.novatec.domain.domainmodel

data class User (
    val phoneNum:String,
    val name:String,
    val surname:String,
    val deviceId : String,
    val userType : String
)