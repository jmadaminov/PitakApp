package com.badcompany.domain


import com.badcompany.domain.ResultWrapper
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.IUserRepository
import com.badcompany.domain.usecases.LogUserIn
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import testUserCredentials


class LogUserInTest {

    val repo: IUserRepository = mockk()

    val useCase = LogUserIn(repo)

    @Test
    fun `User Log In`() = runBlockingTest {
        val testUserLogin = "token"

        val userCredentials = testUserCredentials()

        coEvery { repo.loginUser(userCredentials) } returns ResultWrapper.build { testUserLogin }

        useCase.execute(userCredentials)

        coVerify(exactly = 1) {  repo.loginUser(userCredentials) }



    }


}