package org.sic4change.chuqabp.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.UserRepository
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.testshared.mockedUser

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getUser saves remote data to local`() {
        runBlocking {
            userRepository.getUser()
            verify(localDataSource).getUser()
        }
    }

    @Test
    fun `getUser with email local data source`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(remoteDataSource.getUser(user.email)).thenReturn(user)

            userRepository.getUser(user.email)
            verify(remoteDataSource).getUser(user.email)
        }
    }

    @Test
    fun `login user from remote`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(remoteDataSource.login(user.email, "password")).thenReturn("logged")

            val result = userRepository.login(user.email, "password")
            assertEquals("logged",result )
        }
    }

    @Test
    fun `forgotPassword calls forgot password repository`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")

            userRepository.forgotPassword(user.email)
            verify(remoteDataSource).forgotPassword(user.email)
        }
    }

    @Test
    fun `create user create data local`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXX")
            whenever(remoteDataSource.createUser(user.email, "password")).thenReturn("created")

            val result = userRepository.createUser(user.email, "password")
            assertEquals("created",result )
        }
    }


}