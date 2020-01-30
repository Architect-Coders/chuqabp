package org.sic4change.chuqabp.usescases

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.UserRepository
import org.sic4change.testshared.mockedUser
import org.sic4change.usescases.ForgotPassword

@RunWith(MockitoJUnitRunner::class)
class ForgotPasswordTest {

    @Mock
    lateinit var userRepository: UserRepository

    lateinit var forgotPassword: ForgotPassword

    @Before
    fun setUp() {
        forgotPassword = ForgotPassword(userRepository)
    }

    @Test
    fun `invoke calls create case repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            forgotPassword.invoke(user.email)
            verify(userRepository).forgotPassword(user.email)
        }
    }


}