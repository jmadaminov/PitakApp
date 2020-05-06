package com.badcompany.remote.model

/**
 * Created by jahon on 12-Apr-20
 */
data class AuthSuccessResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: UserInfoModel? = null)

data class UserInfoModel(val phoneNum: String? = null,
                         val name: String? = null,
                         val surname: String? = null,
                         val jwt: String? = null,
                         val role: String? = null)
