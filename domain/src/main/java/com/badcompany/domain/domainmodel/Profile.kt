package com.badcompany.domain.domainmodel

data class Profile( val phoneNum: String,
               val name: String,
               val surname: String,
               val id: String,
               val image: Image?=null)