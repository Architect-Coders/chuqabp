package org.sic4change.chuqabp.updateperson

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
import org.sic4change.chuqabp.course.ui.main.updateperson.UpdatePersonViewModel
import org.sic4change.domain.Person

import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.persons.FindPersonById
import org.sic4change.usescases.persons.UpdatePerson

@RunWith(MockitoJUnitRunner::class)
class UpdatePersonViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findCaseById: FindPersonById

    @Mock
    lateinit var updateCase: UpdatePerson

    @Mock
    lateinit var observerCase: Observer<Person>

    @Mock
    lateinit var observerShowingUpdateCaseError: Observer<Event<Boolean>>

    private lateinit var vm: UpdatePersonViewModel

    @Before
    fun setUp() {
        vm = UpdatePersonViewModel("XXXXX", findCaseById, updateCase, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the case`() {
        runBlocking {
            val case = mockedPerson.copy(id = "XXXXX")
            whenever(findCaseById.invoke("XXXXX")).thenReturn(case)

            vm = UpdatePersonViewModel("XXXXX", findCaseById, updateCase, Dispatchers.Unconfined)

            vm.person.observeForever(observerCase)
            verify(observerCase).onChanged(vm.person.value)
        }
    }

    @Test
    fun `observing LiveData update case with empty value`(){
        runBlocking {
            vm.showingUpdatePersonError.observeForever(observerShowingUpdateCaseError)

            vm.onUpdatePersonClicked("XXXXXX", mockedPerson.name, mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone,
                mockedPerson.email, mockedPerson.photo, "")

            Assert.assertEquals(true, (vm.showingUpdatePersonError.value)?.peekContent())

        }

    }

    @Test
    fun `observing LiveData update case with ok`(){
        runBlocking {
            vm.showingUpdatePersonError.observeForever(observerShowingUpdateCaseError)

            vm.onUpdatePersonClicked("XXXXXX", mockedPerson.name, mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone,
                mockedPerson.email, mockedPerson.photo, mockedPerson.location)

            Assert.assertEquals(false, (vm.showingUpdatePersonError.value)?.peekContent())

        }

    }



}