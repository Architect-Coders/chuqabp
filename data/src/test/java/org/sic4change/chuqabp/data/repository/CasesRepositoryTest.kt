package org.sic4change.chuqabp.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.data.repository.CasesRepository
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.testshared.mockedCase
import org.sic4change.testshared.mockedUser

@RunWith(MockitoJUnitRunner::class)
class CasesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var casesRepository: CasesRepository

    @Before
    fun setUp() {
        casesRepository = CasesRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getCases saves remote data to local`() {
        runBlocking {
            val remotesCases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(remoteDataSource.getCases(localDataSource.getUser()?.id)).thenReturn(remotesCases)

            casesRepository.getCases()
            verify(localDataSource).insertCases(remotesCases)
        }
    }

    @Test
    fun `findById local data source`() {
        runBlocking {
            val localCases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(localDataSource.findById("XXXXX")).thenReturn(localCases[0])

            val result = casesRepository.findCaseById("XXXXX")
            assertEquals(localCases[0], result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {
            val localCase = mockedCase.copy(id = "XXXXX")
            casesRepository.updateCase(localCase)
            verify(localDataSource).updateCase(localCase)
        }
    }

    @Test
    fun `createCase save data local`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            val user = mockedUser.copy(id = "YYYYY")

            val localCases = listOf(case)
            whenever(localDataSource.getCases()).thenReturn(localCases)
            whenever(localDataSource.getUser()).thenReturn(user)

            casesRepository.createCase(case)
            assertEquals(casesRepository.getCases().size, 1)
        }
    }

    @Test
    fun `updateCase update data local`() {
        runBlocking {
            var case = mockedCase.copy(id = "XXXXX", name = "Beatriz")
            val user = mockedUser.copy(id = "YYYYY")

            whenever(localDataSource.findById("XXXXX")).thenReturn(case)
            whenever(localDataSource.getUser()).thenReturn(user)

            casesRepository.updateCase(case)
            assertEquals(casesRepository.findCaseById("XXXXX").name, case.name)
        }
    }

    @Test
    fun `removeCase delete data local`() {
        runBlocking {
            var case = mockedCase.copy(id = "XXXXX")

            whenever(localDataSource.findById("XXXXX")).thenReturn(null)

            casesRepository.deleteCase(case.id)
            assertEquals(casesRepository.findCaseById("XXXXX"), null)
        }
    }


}