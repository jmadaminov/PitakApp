package com.novatec.data.model


data class ProfileEntity( val phoneNum: String,
                       val name: String,
                       val surname: String,
                       val id: String,
                       val image: ImageEntity?=null)