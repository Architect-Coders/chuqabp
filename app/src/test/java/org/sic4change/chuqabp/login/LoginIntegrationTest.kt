package org.sic4change.chuqabp.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
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
import org.sic4change.chuqabp.FakeLocalDataSource
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.chuqabp.initMockedDi
import org.sic4change.data.source.LocalDataSource
import org.sic4change.usescases.CreateUser
import org.sic4change.usescases.ForgotPassword
import org.sic4change.usescases.GetSavedUser
import org.sic4change.usescases.Login

@RunWith(MockitoJUnitRunner::class)
class LoginIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var navigateToMainViewObserver: Observer<Event<Unit>>

    private lateinit var vm: LoginViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { LoginViewModel(get(), get(), get(), get(), get()) }
            factory { Login(get()) }
            factory { ForgotPassword(get()) }
            factory { CreateUser(get()) }
            factory { GetSavedUser(get()) }


        }
        initMockedDi(vmModule)

        vm = get()
        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        //localDataSource.user = defaultFakeUser

    }

    @Test
    fun `observing LiveData autologin user`() {
        vm.navigateToMain.observeForever(navigateToMainViewObserver)
        runBlocking {
            verify(navigateToMainViewObserver).onChanged(vm.navigateToMain.value)

        }
    }

    @Test
    fun `observing LiveData login user`() {
        vm.navigateToMain.observeForever(navigateToMainViewObserver)
        runBlocking {
            vm.onLoginClicked("aaron.asencio.tavio@gmail.com", "password")
            verify(navigateToMainViewObserver).onChanged(vm.navigateToMain.value)

        }
    }

    @Test
    fun `observing LiveData create user`() {
        vm.navigateToMain.observeForever(navigateToMainViewObserver)
        runBlocking {
            vm.onCreateNewUserClicked("aaron.asencio.tavio@gmail.com", "password")
            verify(navigateToMainViewObserver).onChanged(vm.navigateToMain.value)

        }
    }

}