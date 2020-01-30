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
import org.sic4change.usescases.UpdateCase

@RunWith(MockitoJUnitRunner::class)
class UpdateCaseTest {

    @Mock
    lateinit var casesRepository: CasesRepository

    lateinit var updateCase: UpdateCase

    @Before
    fun setUp() {
        updateCase = UpdateCase(casesRepository)
    }

    @Test
    fun `invoke calls update case repository`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            updateCase.invoke(case)
            verify(casesRepository).updateCase(case)
        }
    }

    @Test
    fun `change value of case`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX", name = "Beatriz")
            whenever(casesRepository.findCaseById(case.id)).thenReturn(case)

            updateCase.invoke(case)

            assertEquals(case.name, casesRepository.findCaseById("XXXXX").name)
        }
    }

}