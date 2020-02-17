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
import org.sic4change.usescases.DeletePerson

@RunWith(MockitoJUnitRunner::class)
class DeletePersonTest {

    @Mock
    lateinit var personsRepository: PersonsRepository

    lateinit var deletePerson: DeletePerson

    @Before
    fun setUp() {
        deletePerson = DeletePerson(personsRepository)
    }

    @Test
    fun `invoke calls delete repository`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            deletePerson.invoke(person.id)
            verify(personsRepository).deletePerson(person.id)
        }
    }

    @Test
    fun `delete person`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            whenever(personsRepository.findPersonById(person.id)).thenReturn(null)
            
            deletePerson.invoke(person.id)

            assertEquals(null, personsRepository.findPersonById("XXXXX"))
        }
    }

}