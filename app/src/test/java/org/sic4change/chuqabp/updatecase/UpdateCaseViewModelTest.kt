package org.sic4change.chuqabp.updatecase

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
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseViewModel
import org.sic4change.domain.Case

import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.*

@RunWith(MockitoJUnitRunner::class)
class UpdateCaseViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findCaseById: FindCaseById

    @Mock
    lateinit var updateCase: UpdateCase

    @Mock
    lateinit var observerCase: Observer<Case>

    @Mock
    lateinit var observerShowingUpdateCaseError: Observer<Event<Boolean>>

    private lateinit var vm: UpdateCaseViewModel

    @Before
    fun setUp() {
        vm = UpdateCaseViewModel("XXXXX", findCaseById, updateCase, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the case`() {
        runBlocking {
            val case = mockedCase.copy(id = "XXXXX")
            whenever(findCaseById.invoke("XXXXX")).thenReturn(case)

            vm = UpdateCaseViewModel("XXXXX", findCaseById, updateCase, Dispatchers.Unconfined)

            vm.case.observeForever(observerCase)
            verify(observerCase).onChanged(vm.case.value)
        }
    }

    @Test
    fun `observing LiveData update case with empty value`(){
        runBlocking {
            vm.showingUpdateCaseError.observeForever(observerShowingUpdateCaseError)

            vm.onUpdateCaseClicked("XXXXXX", mockedCase.name, mockedCase.surnames, mockedCase.birthdate, mockedCase.phone,
                mockedCase.email, mockedCase.photo, "")

            Assert.assertEquals(true, (vm.showingUpdateCaseError.value)?.peekContent())

        }

    }

    @Test
    fun `observing LiveData update case with ok`(){
        runBlocking {
            vm.showingUpdateCaseError.observeForever(observerShowingUpdateCaseError)

            vm.onUpdateCaseClicked("XXXXXX", mockedCase.name, mockedCase.surnames, mockedCase.birthdate, mockedCase.phone,
                mockedCase.email, mockedCase.photo, mockedCase.location)

            Assert.assertEquals(false, (vm.showingUpdateCaseError.value)?.peekContent())

        }

    }



}