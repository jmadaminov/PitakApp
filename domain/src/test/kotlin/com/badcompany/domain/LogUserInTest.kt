package com.novatec.domain


import com.novatec.core.Constants
import com.novatec.domain.repository.UserRepository
import com.novatec.domain.usecases.LogUserIn
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import testPhoneNum


class LogUserInTest {

    val repo: UserRepository = mockk()

    val useCase = LogUserIn(repo)

    @Test
    fun `User Log In`() = runBlockingTest {
        val testUserLogin = "token"

        val phoneNum = testPhoneNum()

//        coEvery { repo.loginUser(userCredentials) } returns ResultWrapper.build { testUserLogin }

        useCase.execute(hashMapOf(Pair(Constants.TXT_PHONE_NUMBER, phoneNum),
                                  Pair(Constants.TXT_UDID, "")))

        coVerify(exactly = 1) { repo.loginUser(phoneNum, "") }
    }


    @Test
    fun `User Log In Error`() = runBlockingTest {
        val testUserLogin = "token"

        val phoneNum = testPhoneNum()

//        coEvery { repo.loginUser(userCredentials) } returns ResultWrapper.build { throw UserRepositoryException }

        val result = useCase.execute(hashMapOf(Pair(Constants.TXT_PHONE_NUMBER, phoneNum),
                                               Pair(Constants.TXT_UDID, "")))

//        assert(result is ResultWrapper.Error)

        coVerify(exactly = 1) { repo.loginUser(phoneNum, "") }
    }


}