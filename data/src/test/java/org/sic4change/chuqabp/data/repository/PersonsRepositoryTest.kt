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
import org.sic4change.data.repository.PersonsRepository
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.testshared.mockedPerson
import org.sic4change.testshared.mockedUser

@RunWith(MockitoJUnitRunner::class)
class PersonsRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var personsRepository: PersonsRepository

    @Before
    fun setUp() {
        personsRepository = PersonsRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getPersons saves remote data to local`() {
        runBlocking {
            val remotesPersons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(remoteDataSource.getPersons(localDataSource.getUser()?.id)).thenReturn(remotesPersons)
            whenever((localDataSource.getPersons())).thenReturn(emptyList())

            personsRepository.getPersons()
            verify(localDataSource).insertPersons(remotesPersons)
        }
    }

    @Test
    fun `findById local data source`() {
        runBlocking {
            val localPersons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(localDataSource.findById("XXXXX")).thenReturn(localPersons[0])

            val result = personsRepository.findPersonById("XXXXX")
            assertEquals(localPersons[0], result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {
            val localPerson = mockedPerson.copy(id = "XXXXX")
            personsRepository.updatePerson(localPerson)
            verify(localDataSource).updatePerson(localPerson)
        }
    }

    @Test
    fun `createPerson save data local`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            val user = mockedUser.copy(id = "YYYYY")

            val localPersons = listOf(person)
            whenever(localDataSource.getPersons()).thenReturn(localPersons)
            whenever(localDataSource.getUser()).thenReturn(user)

            personsRepository.createPerson(person)
            assertEquals(personsRepository.getPersons().size, 1)
        }
    }

    @Test
    fun `updatePerson update data local`() {
        runBlocking {
            var person = mockedPerson.copy(id = "XXXXX", name = "Beatriz")
            val user = mockedUser.copy(id = "YYYYY")

            whenever(localDataSource.findById("XXXXX")).thenReturn(person)
            whenever(localDataSource.getUser()).thenReturn(user)

            personsRepository.updatePerson(person)
            assertEquals(personsRepository.findPersonById("XXXXX").name, person.name)
        }
    }

    @Test
    fun `removePerson delete data local`() {
        runBlocking {
            var person = mockedPerson.copy(id = "XXXXX")

            whenever(localDataSource.findById("XXXXX")).thenReturn(null)

            personsRepository.deletePerson(person.id)
            assertEquals(personsRepository.findPersonById("XXXXX"), null)
        }
    }


}