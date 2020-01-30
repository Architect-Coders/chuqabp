package org.sic4change.chuqabp.usescases

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.CasesRepository
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.FindCaseById

@RunWith(MockitoJUnitRunner::class)
class FindCaseByIdTest {

    @Mock
    lateinit var casesRepository: CasesRepository

    lateinit var findCaseById: FindCaseById

    @Before
    fun setUp() {
        findCaseById = FindCaseById(casesRepository)
    }

    @Test
    fun `invoke calls cases repository`() {
        runBlocking {
            val case = mockedCase.copy(id ="XXXXX")
            whenever(casesRepository.findCaseById("XXXXX")).thenReturn(case)

            val result = findCaseById.invoke("XXXXX")

            assertEquals(case, result)
        }
    }


}