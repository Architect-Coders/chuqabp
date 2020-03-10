package org.sic4change.chuqabp.login

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
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.testshared.mockedUser
import org.sic4change.usescases.users.CreateUser
import org.sic4change.usescases.users.ForgotPassword
import org.sic4change.usescases.users.GetSavedUser
import org.sic4change.usescases.users.Login

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var login: Login

    @Mock
    lateinit var forgotPassword: ForgotPassword

    @Mock
    lateinit var createUser: CreateUser

    @Mock
    lateinit var getSavedUser: GetSavedUser

    @Mock
    lateinit var observerNavigateToMainView: Observer<Event<Unit>>

    @Mock
    lateinit var observerShowingLoginView: Observer<Boolean>

    @Mock
    lateinit var observerLoginErrors: Observer<Event<String>>

    @Mock
    lateinit var observerNavigateToCreateAccount: Observer<Event<Unit>>

    @Mock
    lateinit var observerShowingForgotPasswordResult: Observer<Event<Boolean>>

    @Mock
    lateinit var observerShowingCreateAccountErrors: Observer<Event<String>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    private lateinit var vm: LoginViewModel

    @Before
    fun setUp() {
        vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)
    }

    @Test
    fun `autologin when user exist`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXXX")
            whenever(getSavedUser.invoke()).thenReturn(user)

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.navigateToMain.observeForever(observerNavigateToMainView)
            verify(observerNavigateToMainView).onChanged(vm.navigateToMain.value)
        }
    }

    @Test
    fun `autologin cancel when user does not exist`() {
        runBlocking {
            whenever(getSavedUser.invoke()).thenReturn(null)

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.showingLoginView.observeForever(observerShowingLoginView)
            Assert.assertEquals(true, vm.showingLoginView.value)
        }
    }

    @Test
    fun `show loading when login`() {
        runBlocking {
            vm.loading.observeForever(observerLoading)

            vm.onLoginClicked(mockedUser.email, "password")
            verify(observerLoading).onChanged(vm.loading.value)

        }
    }

    @Test
    fun `login whith incorrect values`() {
        runBlocking {
            whenever(login.invoke(mockedUser.email, "password")).thenReturn("no logged")

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.showingLoginErrors.observeForever(observerLoginErrors)

            vm.onLoginClicked(mockedUser.email, "password")
            Assert.assertEquals("no logged", vm.showingLoginErrors.value?.peekContent())

        }
    }

    @Test
    fun `login whith correct values`() {
        runBlocking {
            val user = mockedUser.copy(id = "XXXXXX")
            whenever(getSavedUser.invoke()).thenReturn(user)
            whenever(login.invoke(mockedUser.email, "password")).thenReturn("logged")

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.navigateToMain.observeForever(observerNavigateToMainView)

            vm.onLoginClicked(mockedUser.email, "password")

            verify(observerNavigateToMainView).onChanged(vm.navigateToMain.value)

        }
    }

    @Test
    fun `navigate from login view to create account view`() {
        runBlocking {
            vm.navigateToCreateAccount.observeForever(observerNavigateToCreateAccount)

            vm.onCreateAccountClicked()
            verify(observerNavigateToCreateAccount).onChanged(vm.navigateToCreateAccount.value)
        }
    }

    @Test
    fun `forgot password whith incorrect values`() {
        runBlocking {
            whenever(forgotPassword.invoke(mockedUser.email)).thenReturn(false)

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.showingForgotPasswordResult.observeForever(observerShowingForgotPasswordResult)

            vm.onForgotPasswordClicked(mockedUser.email)

            Assert.assertEquals(false, vm.showingForgotPasswordResult.value?.peekContent())

        }
    }

    @Test
    fun `forgot password whith correct values`() {
        runBlocking {
            whenever(forgotPassword.invoke(mockedUser.email)).thenReturn(true)

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.showingForgotPasswordResult.observeForever(observerShowingForgotPasswordResult)

            vm.onForgotPasswordClicked(mockedUser.email)

            Assert.assertEquals(true, vm.showingForgotPasswordResult.value?.peekContent())

        }
    }

    @Test
    fun `create user whith incorrect values`() {
        runBlocking {
            whenever(createUser.invoke(mockedUser.email, "password")).thenReturn("error")
            whenever(getSavedUser.invoke()).thenReturn(null)

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.showingCreateAccountErrors.observeForever(observerShowingCreateAccountErrors)

            vm.onCreateNewUserClicked(mockedUser.email, "password")
            verify(observerShowingCreateAccountErrors).onChanged(vm.showingCreateAccountErrors.value)

        }
    }

    @Test
    fun `create user whith correct values`() {
        runBlocking {
            whenever(createUser.invoke(mockedUser.email, "password")).thenReturn("created")
            whenever(getSavedUser.invoke()).thenReturn(mockedUser.copy(id="XXXXX"))

            vm = LoginViewModel(login, forgotPassword, createUser, getSavedUser, Dispatchers.Unconfined)

            vm.navigateToMain.observeForever(observerNavigateToMainView)

            vm.onCreateNewUserClicked(mockedUser.email, "password")
            verify(observerNavigateToMainView).onChanged(vm.navigateToMain.value)

        }
    }

}