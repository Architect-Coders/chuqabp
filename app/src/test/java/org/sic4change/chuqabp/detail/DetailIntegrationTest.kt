package org.sic4change.chuqabp.detail


import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.domain.Person

import org.junit.Before
import org.junit.Test
import org.sic4change.chuqabp.FakeLocalDataSource
import org.sic4change.chuqabp.course.ui.main.detail.DetailViewModel
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.chuqabp.defaultFakePersons
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.DeletePerson
import org.sic4change.usescases.FindPersonById

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.get

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var personObserver: Observer<Person>

    @Mock
    lateinit var deleteObserver: Observer<Boolean>

    private lateinit var vm: DetailViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: String) -> DetailViewModel(id, get(), get(), get()) }
            factory{ FindPersonById(get()) }
            factory{ DeletePerson(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf("AAAAA")}

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.persons = defaultFakePersons
    }

    @Test
    fun `observing LiveData finds the person`() {
        vm.person.observeForever(personObserver)
        runBlocking {
            vm.findPersonById()
            verify(personObserver).onChanged(mockedPerson.copy("AAAAA"))
        }
    }

    @Test
    fun `delete person`() {
        vm.person.observeForever(personObserver)
        vm.deleted.observeForever(deleteObserver)
        runBlocking {
            vm.deletePerson()
            assertEquals(3, localDataSource.persons.size)
        }
    }

}