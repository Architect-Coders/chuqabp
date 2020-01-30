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
import org.sic4change.usescases.GetCases

@RunWith(MockitoJUnitRunner::class)
class GetCasesTest {

    @Mock
    lateinit var casesRepository: CasesRepository

    lateinit var getCases: GetCases

    @Before
    fun setUp() {
        getCases = GetCases(casesRepository)
    }

    @Test
    fun `invoke calls cases repository`() {
        runBlocking {
            val localCases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(casesRepository.getCases()).thenReturn(localCases)
            
            val result = getCases.invoke()

            assertEquals(localCases, result)
        }
    }


}