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
import org.sic4change.usescases.GetPersons

@RunWith(MockitoJUnitRunner::class)
class GetPersonsTest {

    @Mock
    lateinit var personsRepository: PersonsRepository

    lateinit var getPersons: GetPersons

    @Before
    fun setUp() {
        getPersons = GetPersons(personsRepository)
    }

    @Test
    fun `invoke calls persons repository`() {
        runBlocking {
            val localPersons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(personsRepository.getPersons()).thenReturn(localPersons)
            
            val result = getPersons.invoke()

            assertEquals(localPersons, result)
        }
    }


}