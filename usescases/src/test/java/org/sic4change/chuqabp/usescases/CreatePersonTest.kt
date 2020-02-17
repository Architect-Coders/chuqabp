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
import org.sic4change.data.repository.PersonsRepository
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.CreatePerson

@RunWith(MockitoJUnitRunner::class)
class CreatePersonTest {

    @Mock
    lateinit var personsRepository: PersonsRepository

    lateinit var createPerson: CreatePerson

    @Before
    fun setUp() {
        createPerson = CreatePerson(personsRepository)
    }

    @Test
    fun `invoke calls create person repository`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            createPerson.invoke(person)
            verify(personsRepository).createPerson(person)
        }
    }

    @Test
    fun `create person`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            whenever(personsRepository.findPersonById(person.id)).thenReturn(person)

            createPerson.invoke(person)

            assertEquals(person, personsRepository.findPersonById("XXXXX"))
        }
    }

}