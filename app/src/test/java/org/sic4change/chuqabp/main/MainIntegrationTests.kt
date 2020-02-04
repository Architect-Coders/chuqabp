package org.sic4change.chuqabp.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.chuqabp.course.ui.main.main.MainViewModel
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.usescases.GetCases
import org.koin.test.get
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.defaultFakeCases
import org.sic4change.domain.Case

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var requestLocationPermissionObserver: Observer<Event<Unit>>

    @Mock
    lateinit var casesObserver: Observer<List<Case>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetCases (get()) }
        }
        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server`() {
        vm.requestLocationPermission.observeForever(requestLocationPermissionObserver)
        vm.cases.observeForever(casesObserver)
        vm.onCoarsePermissionRequest()
        verify(casesObserver).onChanged(defaultFakeCases)
    }


}