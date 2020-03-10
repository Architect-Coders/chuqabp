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
import org.sic4change.usescases.users.GetSavedUser

@RunWith(MockitoJUnitRunner::class)
class GetSavedUserTest {

    @Mock
    lateinit var userRepository: UserRepository

    lateinit var getSavedUser: GetSavedUser

    @Before
    fun setUp() {
        getSavedUser =
            GetSavedUser(userRepository)
    }

    @Test
    fun `invoke calls user repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(userRepository.getUser()).thenReturn(user)
            
            getSavedUser.invoke()

            assertEquals(user, userRepository.getUser())
        }
    }


}