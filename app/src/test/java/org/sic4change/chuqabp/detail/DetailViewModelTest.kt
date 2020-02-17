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
import org.sic4change.domain.Person
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.DeletePerson
import org.sic4change.usescases.FindPersonById

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findPersonById: FindPersonById

    @Mock
    lateinit var deletePerson: DeletePerson

    @Mock
    lateinit var observerPerson: Observer<Person>

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
        vm = DetailViewModel(id, findPersonById, deletePerson, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the person`() {
        runBlocking {
            val person = mockedPerson.copy(id = id)
            whenever(findPersonById.invoke(id)).thenReturn(person)

            vm = DetailViewModel(id, findPersonById, deletePerson, Dispatchers.Unconfined)

            vm.person.observeForever(observerPerson)
            verify(observerPerson).onChanged(vm.person.value)
        }
    }

    @Test
    fun `observing LiveData change title`() {
        runBlocking {
            val person = mockedPerson.copy(id = id)
            whenever(findPersonById.invoke(id)).thenReturn(person)

            vm = DetailViewModel(id, findPersonById, deletePerson, Dispatchers.Unconfined)

            vm.title.observeForever(observerTitle)
            Assert.assertEquals(person.name + " " + person.surnames, vm.title.value)
        }
    }

    @Test
    fun `observing LiveData change url photo`() {
        runBlocking {
            val person = mockedPerson.copy(id = id)
            whenever(findPersonById.invoke(id)).thenReturn(person)

            vm = DetailViewModel(id, findPersonById, deletePerson, Dispatchers.Unconfined)

            vm.url.observeForever(observerUrl)
            Assert.assertEquals(person.photo, vm.url.value)
        }
    }

    @Test
    fun `observing LiveData delete person`() {
        runBlocking {

            vm.deletePerson()

            vm.deleted.observeForever(observerDelete)
            Assert.assertEquals(true, vm.deleted.value)
        }
    }

}
