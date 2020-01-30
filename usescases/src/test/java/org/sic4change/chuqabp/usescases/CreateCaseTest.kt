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
import org.sic4change.usescases.CreateCase

@RunWith(MockitoJUnitRunner::class)
class CreateCaseTest {

    @Mock
    lateinit var casesRepository: CasesRepository

    lateinit var createCase: CreateCase

    @Before
    fun setUp() {
        createCase = CreateCase(casesRepository)
    }

    @Test
    fun `invoke calls create case repository`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            createCase.invoke(case)
            verify(casesRepository).createCase(case)
        }
    }

    @Test
    fun `create case`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            whenever(casesRepository.findCaseById(case.id)).thenReturn(case)

            createCase.invoke(case)

            assertEquals(case, casesRepository.findCaseById("XXXXX"))
        }
    }

}