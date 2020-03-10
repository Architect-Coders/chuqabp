package org.sic4change.chuqabp.updateperson


import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.domain.Person

import org.junit.Before
import org.junit.Test
import org.sic4change.chuqabp.FakeLocalDataSource
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.persons.FindPersonById

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.get
import org.sic4change.chuqabp.course.ui.main.updateperson.UpdatePersonViewModel
import org.sic4change.chuqabp.defaultFakePersons
import org.sic4change.usescases.persons.UpdatePerson

@RunWith(MockitoJUnitRunner::class)
class UpdateIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var personObserver: Observer<Person>

    private lateinit var vm: UpdatePersonViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: String) -> UpdatePersonViewModel(id, get(), get(), get()) }
            factory{ FindPersonById(get()) }
            factory{ UpdatePerson(get()) }
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
            assertEquals(mockedPerson.copy("AAAAA").id, vm.person.value?.id)
        }
    }

    @Test
    fun `update Case`() {
        vm.person.observeForever(personObserver)
        vm.findPersonById()
        runBlocking {
            val id = vm.person.value?.id
            id?.let { vm.onUpdatePersonClicked(it, "Pepe", mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone, mockedPerson.email, mockedPerson.photo, mockedPerson.location)}
            assertEquals("Pepe", id?.let { localDataSource.findPersonById(it).name })
        }
    }

}