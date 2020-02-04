package org.sic4change.chuqabp.newcase

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
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseViewModel
import org.sic4change.data.source.LocalDataSource
import org.sic4change.testshared.mockedCase
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetLocation

@RunWith(MockitoJUnitRunner::class)
class NewCaseIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var showingCreateCaseErrorObserver: Observer<Event<Boolean>>

    private lateinit var vm: NewCaseViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { NewCaseViewModel(get(), get(), get()) }
            factory { GetLocation(get()) }
            factory { CreateCase(get()) }
        }

        initMockedDi(vmModule)
        vm = get()

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.user = defaultFakeUser
        localDataSource.cases = defaultFakeCases
    }


    @Test
    fun `create case is added in local data source`() {
        vm.showingCreateCaseError.observeForever(showingCreateCaseErrorObserver)
        vm.onCreateCaseClicked(mockedCase.name, mockedCase.surnames, mockedCase.birthdate, mockedCase.phone, mockedCase.email, mockedCase.photo, mockedCase.location)
        runBlocking {
            assertTrue(localDataSource.cases.size==5)
        }
    }


}