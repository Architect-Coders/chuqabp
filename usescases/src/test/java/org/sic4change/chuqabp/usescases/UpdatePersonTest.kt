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
import org.sic4change.usescases.persons.UpdatePerson

@RunWith(MockitoJUnitRunner::class)
class UpdatePersonTest {

    @Mock
    lateinit var personsRepository: PersonsRepository

    lateinit var updatePerson: UpdatePerson

    @Before
    fun setUp() {
        updatePerson =
            UpdatePerson(personsRepository)
    }

    @Test
    fun `invoke calls update person repository`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX")
            updatePerson.invoke(person)
            verify(personsRepository).updatePerson(person)
        }
    }

    @Test
    fun `change value of person`() {
        runBlocking {
            val person = mockedPerson.copy(id = "XXXXX", name = "Beatriz")
            whenever(personsRepository.findPersonById(person.id)).thenReturn(person)

            updatePerson.invoke(person)

            assertEquals(person.name, personsRepository.findPersonById("XXXXX").name)
        }
    }

}