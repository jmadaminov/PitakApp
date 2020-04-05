package com.badcompany.pitak.data

import com.badcompany.core.ResultWrapper
import com.badcompany.pitak.data.model.LoggedInUser
import java.io.IOException
import java.lang.Exception

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): ResultWrapper<Exception, LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return ResultWrapper.Success(fakeUser)
        } catch (e: Throwable) {
            return ResultWrapper.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

