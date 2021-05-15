package com.novatec.domain.domainmodel

data class AuthBody(val id: String? = null,
                    val phoneNum: String? = null,
                    val name: String? = null,
                    val surname: String? = null,
                    val jwt: String? = null,
                    val rating: Float = 0F,
                    val defCarId: String? = null,
                    val role: String? = null,
                    val image: Image?=null
)