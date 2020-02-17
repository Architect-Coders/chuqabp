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
import org.sic4change.domain.Person

import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.GetPersons
import org.sic4change.usescases.RefreshPersons

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPersons: GetPersons

    @Mock
    lateinit var refreshPersons: RefreshPersons

    @Mock
    lateinit var observerPermission: Observer<Event<Unit>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerPersons: Observer<List<Person>>

    @Mock
    lateinit var observerNavigateToPerson: Observer<Event<String>>


    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getPersons, refreshPersons, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`(){
        vm.requestLocationPermission.observeForever(observerPermission)
        verify(observerPermission).onChanged(vm.requestLocationPermission.value)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val persons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(getPersons.invoke()).thenReturn(persons)
            vm.requestLocationPermission.observeForever(observerPermission)
            vm.loading.observeForever(observerLoading)

            vm.onCoarsePermissionRequest()

            verify(observerLoading).onChanged(vm.loading.value)
        }
    }

    @Test
    fun `after requesting the permission, getPersons is called`() {
        runBlocking {
            val persons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(getPersons.invoke()).thenReturn(persons)

            vm.requestLocationPermission.observeForever(observerPermission)
            vm.persons.observeForever(observerPersons)

            vm.onCoarsePermissionRequest()

            verify(observerPersons).onChanged(vm.persons.value)
        }

    }

    @Test
    fun `when person clicked, navigate to case`() {
        runBlocking {
            val persons = listOf(mockedPerson.copy(id = "XXXXX"))
            whenever(getPersons.invoke()).thenReturn(persons)

            vm.onCoarsePermissionRequest()

            vm.navigateToPerson.observeForever(observerNavigateToPerson)

            vm.onPersonClicked(persons[0])

            Assert.assertEquals("XXXXX", (vm.navigateToPerson.value)?.peekContent())

        }
    }

}