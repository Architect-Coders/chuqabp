package org.sic4change.chuqabp.detail


import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.domain.Case

import org.junit.Before
import org.junit.Test
import org.sic4change.chuqabp.FakeLocalDataSource
import org.sic4change.chuqabp.course.ui.main.detail.DetailViewModel
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.chuqabp.defaultFakeCases
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.DeleteCase
import org.sic4change.usescases.FindCaseById

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.get

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var caseObserver: Observer<Case>

    @Mock
    lateinit var deleteObserver: Observer<Boolean>

    private lateinit var vm: DetailViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: String) -> DetailViewModel(id, get(), get(), get()) }
            factory{ FindCaseById(get()) }
            factory{ DeleteCase(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf("AAAAA")}

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.cases = defaultFakeCases
    }

    @Test
    fun `observing LiveData finds the case`() {
        vm.case.observeForever(caseObserver)
        runBlocking {
            vm.findCaseById()
            verify(caseObserver).onChanged(mockedCase.copy("AAAAA"))
        }
    }

    @Test
    fun `delete Case`() {
        vm.case.observeForever(caseObserver)
        vm.deleted.observeForever(deleteObserver)
        runBlocking {
            vm.deleteCase()
            assertEquals(3, localDataSource.cases.size)
        }
    }

}