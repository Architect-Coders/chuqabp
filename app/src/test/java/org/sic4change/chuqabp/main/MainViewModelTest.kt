package org.sic4change.chuqabp.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.main.main.MainViewModel
import org.sic4change.domain.Case

import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.GetCases
import org.sic4change.usescases.RefreshCases

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCases: GetCases

    @Mock
    lateinit var refreshCases: RefreshCases

    @Mock
    lateinit var observerPermission: Observer<Event<Unit>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerCases: Observer<List<Case>>

    @Mock
    lateinit var observerNavigateToCase: Observer<Event<String>>


    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getCases, refreshCases, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`(){
        vm.requestLocationPermission.observeForever(observerPermission)
        verify(observerPermission).onChanged(vm.requestLocationPermission.value)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val cases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(getCases.invoke()).thenReturn(cases)
            vm.requestLocationPermission.observeForever(observerPermission)
            vm.loading.observeForever(observerLoading)

            vm.onCoarsePermissionRequest()

            verify(observerLoading).onChanged(vm.loading.value)
        }
    }

    @Test
    fun `after requesting the permission, getCases is called`() {
        runBlocking {
            val cases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(getCases.invoke()).thenReturn(cases)

            vm.requestLocationPermission.observeForever(observerPermission)
            vm.cases.observeForever(observerCases)

            vm.onCoarsePermissionRequest()

            verify(observerCases).onChanged(vm.cases.value)
        }

    }

    @Test
    fun `when case clicked, navigate to case`() {
        runBlocking {
            val cases = listOf(mockedCase.copy(id = "XXXXX"))
            whenever(getCases.invoke()).thenReturn(cases)

            vm.onCoarsePermissionRequest()

            vm.navigateToCase.observeForever(observerNavigateToCase)

            vm.onCaseClicked(cases[0])

            Assert.assertEquals("XXXXX", (vm.navigateToCase.value)?.peekContent())

        }
    }

}