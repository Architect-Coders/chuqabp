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
import org.sic4change.usescases.GetUser

@RunWith(MockitoJUnitRunner::class)
class GetUserTest {

    @Mock
    lateinit var userRepository: UserRepository

    lateinit var getUser: GetUser

    @Before
    fun setUp() {
        getUser = GetUser(userRepository)
    }

    @Test
    fun `invoke calls user repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(userRepository.getUser()).thenReturn(user)
            
            getUser.invoke(user.email)

            assertEquals(user, userRepository.getUser())
        }
    }


}