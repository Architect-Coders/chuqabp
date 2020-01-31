package org.sic4change.chuqabp.detail

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
import org.sic4change.chuqabp.course.ui.main.detail.DetailViewModel
import org.sic4change.domain.Case
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.DeleteCase
import org.sic4change.usescases.FindCaseById

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findCaseById: FindCaseById

    @Mock
    lateinit var deleteCase: DeleteCase

    @Mock
    lateinit var observerCase: Observer<Case>

    @Mock
    lateinit var observerTitle: Observer<String>

    @Mock
    lateinit var observerUrl: Observer<String>

    @Mock
    lateinit var observerDelete: Observer<Boolean>

    private lateinit var vm: DetailViewModel

    private val id = "XXXXX"

    @Before
    fun setUp() {
        vm = DetailViewModel(id, findCaseById, deleteCase, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the case`() {
        runBlocking {
            val case = mockedCase.copy(id = id)
            whenever(findCaseById.invoke(id)).thenReturn(case)

            vm = DetailViewModel(id, findCaseById, deleteCase, Dispatchers.Unconfined)

            vm.case.observeForever(observerCase)
            verify(observerCase).onChanged(vm.case.value)
        }
    }

    @Test
    fun `observing LiveData change title`() {
        runBlocking {
            val case = mockedCase.copy(id = id)
            whenever(findCaseById.invoke(id)).thenReturn(case)

            vm = DetailViewModel(id, findCaseById, deleteCase, Dispatchers.Unconfined)

            vm.title.observeForever(observerTitle)
            Assert.assertEquals(case.name + " " + case.surnames, vm.title.value)
        }
    }

    @Test
    fun `observing LiveData change url photo`() {
        runBlocking {
            val case = mockedCase.copy(id = id)
            whenever(findCaseById.invoke(id)).thenReturn(case)

            vm = DetailViewModel(id, findCaseById, deleteCase, Dispatchers.Unconfined)

            vm.url.observeForever(observerUrl)
            Assert.assertEquals(case.photo, vm.url.value)
        }
    }

    @Test
    fun `observing LiveData delete case`() {
        runBlocking {

            vm.deleteCase()

            vm.deleted.observeForever(observerDelete)
            Assert.assertEquals(true, vm.deleted.value)
        }
    }

}
