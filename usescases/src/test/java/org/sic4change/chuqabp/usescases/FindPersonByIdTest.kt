package org.sic4change.chuqabp.usescases

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.PersonsRepository
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.FindPersonById

@RunWith(MockitoJUnitRunner::class)
class FindPersonByIdTest {

    @Mock
    lateinit var personsRepository: PersonsRepository

    lateinit var findPersonById: FindPersonById

    @Before
    fun setUp() {
        findPersonById = FindPersonById(personsRepository)
    }

    @Test
    fun `invoke calls persons repository`() {
        runBlocking {
            val person = mockedPerson.copy(id ="XXXXX")
            whenever(personsRepository.findPersonById("XXXXX")).thenReturn(person)

            val result = findPersonById.invoke("XXXXX")

            assertEquals(person, result)
        }
    }


}