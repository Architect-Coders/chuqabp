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
import org.sic4change.data.repository.CasesRepository
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.DeleteCase

@RunWith(MockitoJUnitRunner::class)
class DeleteCaseTest {

    @Mock
    lateinit var casesRepository: CasesRepository

    lateinit var deleteCase: DeleteCase

    @Before
    fun setUp() {
        deleteCase = DeleteCase(casesRepository)
    }

    @Test
    fun `invoke calls delete repository`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            deleteCase.invoke(case.id)
            verify(casesRepository).deleteCase(case.id)
        }
    }

    @Test
    fun `delete case`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            whenever(casesRepository.findCaseById(case.id)).thenReturn(null)
            
            deleteCase.invoke(case.id)

            assertEquals(null, casesRepository.findCaseById("XXXXX"))
        }
    }

}