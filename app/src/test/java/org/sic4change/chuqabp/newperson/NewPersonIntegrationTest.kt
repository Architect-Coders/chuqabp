package org.sic4change.chuqabp.newperson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.sic4change.chuqabp.*
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.main.newperson.NewPersonViewModel
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedPerson
import org.sic4change.usescases.CreatePerson
import org.sic4change.usescases.GetLocation

@RunWith(MockitoJUnitRunner::class)
class NewPersonIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var showingCreatePersonErrorObserver: Observer<Event<Boolean>>

    private lateinit var vm: NewPersonViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { NewPersonViewModel(get(), get(), get()) }
            factory { GetLocation(get()) }
            factory { CreatePerson(get()) }
        }

        initMockedDi(vmModule)
        vm = get()

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.user = defaultFakeUser
        localDataSource.persons = defaultFakePersons
    }


    @Test
    fun `create person is added in local data source`() {
        vm.showingCreatePersonError.observeForever(showingCreatePersonErrorObserver)
        vm.onCreatePersonClicked(mockedPerson.name, mockedPerson.surnames, mockedPerson.birthdate, mockedPerson.phone, mockedPerson.email, mockedPerson.photo, mockedPerson.location)
        runBlocking {
            assertTrue(localDataSource.persons.size==5)
        }
    }


}