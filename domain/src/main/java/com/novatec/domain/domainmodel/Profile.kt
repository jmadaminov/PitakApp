package com.novatec.domain.domainmodel

data class Profile( val phoneNum: String,
               val name: String,
               val surname: String,
               val id: Long,
               val image: Image?=null,
)