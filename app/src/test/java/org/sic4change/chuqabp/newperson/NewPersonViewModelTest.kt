package org.sic4change.chuqabp.newperson

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
import org.sic4change.chuqabp.course.ui.main.newperson.NewPersonViewModel

import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.persons.CreatePerson
import org.sic4change.usescases.locations.GetLocation

@RunWith(MockitoJUnitRunner::class)
class NewPersonViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getLocation: GetLocation

    @Mock
    lateinit var createPerson: CreatePerson


    @Mock
    lateinit var observerShowingCreatePersonError: Observer<Event<Boolean>>


    @Mock
    lateinit var observerCurrentLocation: Observer<String>


    private lateinit var vm: NewPersonViewModel

    @Before
    fun setUp() {
        vm = NewPersonViewModel(getLocation, createPerson, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData get location`(){
        runBlocking {
            whenever(getLocation.invoke()).thenReturn(mockedPerson.location)
            vm = NewPersonViewModel(getLocation, createPerson, Dispatchers.Unconfined)

            vm.currentLocation.observeForever(observerCurrentLocation)

            verify(observerCurrentLocation).onChanged(vm.currentLocation.value)
        }

    }

    @Test
    fun `observing LiveData create person with empty value`(){
        runBlocking {
            vm.showingCreatePersonError.observeForever(observerShowingCreatePersonError)

            vm.onCreatePersonClicked(mockedPerson.name, mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone,
                mockedPerson.email, mockedPerson.photo, "")

            Assert.assertEquals(true, (vm.showingCreatePersonError.value)?.peekContent())

        }

    }

    @Test
    fun `observing LiveData create person with ok`(){
        runBlocking {
            vm.showingCreatePersonError.observeForever(observerShowingCreatePersonError)

            vm.onCreatePersonClicked(mockedPerson.name, mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone,
                mockedPerson.email, mockedPerson.photo, mockedPerson.location)

            Assert.assertEquals(false, (vm.showingCreatePersonError.value)?.peekContent())

        }

    }



}