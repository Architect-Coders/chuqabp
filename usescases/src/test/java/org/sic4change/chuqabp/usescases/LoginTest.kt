package org.sic4change.chuqabp.usescases

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.UserRepository
import org.sic4change.testshared.mockedUser
import org.sic4change.usescases.Login

@RunWith(MockitoJUnitRunner::class)
class LoginTest {

    @Mock
    lateinit var userRepository: UserRepository

    lateinit var login: Login

    @Before
    fun setUp() {
        login = Login(userRepository)
    }

    @Test
    fun `invoke login repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(userRepository.login(user.email, "password")).thenReturn("logged")
            
            val result = login.invoke(user.email, "password")

            assertEquals("logged", result)
        }
    }


}