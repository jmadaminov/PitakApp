package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class AuthResponse(val code: Int? = null,
                        val message: String? = null,
                        val data: UserRegBody? = null
)

data class UserRegBody(val password: String? = null)
