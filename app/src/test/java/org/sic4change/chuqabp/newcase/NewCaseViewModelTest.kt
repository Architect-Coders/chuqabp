package org.sic4change.chuqabp.newcase

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
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseViewModel
import org.sic4change.domain.Case

import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetCases
import org.sic4change.usescases.GetLocation

@RunWith(MockitoJUnitRunner::class)
class NewCaseViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getLocation: GetLocation

    @Mock
    lateinit var createCase: CreateCase


    @Mock
    lateinit var observerShowingCreateCaseError: Observer<Event<Boolean>>


    @Mock
    lateinit var observerCurrentLocation: Observer<String>


    private lateinit var vm: NewCaseViewModel

    @Before
    fun setUp() {
        vm = NewCaseViewModel(getLocation, createCase, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData get location`(){
        runBlocking {
            whenever(getLocation.invoke()).thenReturn(mockedCase.location)
            vm = NewCaseViewModel(getLocation, createCase, Dispatchers.Unconfined)

            vm.currentLocation.observeForever(observerCurrentLocation)

            verify(observerCurrentLocation).onChanged(vm.currentLocation.value)
        }

    }

    @Test
    fun `observing LiveData create case with empty value`(){
        runBlocking {
            vm.showingCreateCaseError.observeForever(observerShowingCreateCaseError)

            vm.onCreateCaseClicked(mockedCase.name, mockedCase.surnames, mockedCase.birthdate, mockedCase.phone,
                mockedCase.email, mockedCase.photo, "")

            Assert.assertEquals(true, (vm.showingCreateCaseError.value)?.peekContent())

        }

    }

    @Test
    fun `observing LiveData create case with ok`(){
        runBlocking {
            vm.showingCreateCaseError.observeForever(observerShowingCreateCaseError)

            vm.onCreateCaseClicked(mockedCase.name, mockedCase.surnames, mockedCase.birthdate, mockedCase.phone,
                mockedCase.email, mockedCase.photo, mockedCase.location)

            Assert.assertEquals(false, (vm.showingCreateCaseError.value)?.peekContent())

        }

    }



}