package org.sic4change.chuqabp.updatecase


import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.domain.Case

import org.junit.Before
import org.junit.Test
import org.sic4change.chuqabp.FakeLocalDataSource
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.chuqabp.defaultFakeCases
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.FindCaseById

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.get
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseViewModel
import org.sic4change.usescases.UpdateCase

@RunWith(MockitoJUnitRunner::class)
class UpdateIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var caseObserver: Observer<Case>

    private lateinit var vm: UpdateCaseViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: String) -> UpdateCaseViewModel(id, get(), get(), get()) }
            factory{ FindCaseById(get()) }
            factory{ UpdateCase(get()) }
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
            assertEquals(mockedCase.copy("AAAAA").id, vm.case.value?.id)
        }
    }

    @Test
    fun `update Case`() {
        vm.case.observeForever(caseObserver)
        vm.findCaseById()
        runBlocking {
            val id = vm.case.value?.id
            id?.let { vm.onUpdateCaseClicked(it, "Pepe", mockedCase.surnames, mockedCase.birthdate, mockedCase.phone, mockedCase.email, mockedCase.photo, mockedCase.location)}
            assertEquals("Pepe", id?.let { localDataSource.findById(it).name })
        }
    }

}