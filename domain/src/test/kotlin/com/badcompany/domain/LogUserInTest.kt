package com.badcompany.domain


import com.badcompany.domain.repository.UserRepository
import com.badcompany.domain.usecases.LogUserIn
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

        useCase.execute(phoneNum)

        coVerify(exactly = 1) { repo.loginUser(phoneNum) }
    }


    @Test
    fun `User Log In Error`() = runBlockingTest {
        val testUserLogin = "token"

        val phoneNum = testPhoneNum()

//        coEvery { repo.loginUser(userCredentials) } returns ResultWrapper.build { throw UserRepositoryException }

        val result = useCase.execute(phoneNum)

//        assert(result is ResultWrapper.Error)

        coVerify(exactly = 1) { repo.loginUser(phoneNum) }
    }



}