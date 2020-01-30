package org.sic4change.chuqabp.usescases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.UserRepository
import org.sic4change.testshared.mockedUser
import org.sic4change.usescases.CreateUser

@RunWith(MockitoJUnitRunner::class)
class CreateUserTest {

    @Mock
    lateinit var userRepository: UserRepository

    lateinit var createUser: CreateUser

    @Before
    fun setUp() {
        createUser = CreateUser(userRepository)
    }

    @Test
    fun `invoke calls create case repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            createUser.invoke(user.email, "password")
            verify(userRepository).createUser(user.email, "password")
        }
    }

    @Test
    fun `create case`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(userRepository.getUser()).thenReturn(user)

            createUser.invoke(user.email, "password")

            assertEquals(user, userRepository.getUser())
        }
    }

}