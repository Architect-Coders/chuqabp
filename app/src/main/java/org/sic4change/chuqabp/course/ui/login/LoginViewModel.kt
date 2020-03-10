package org.sic4change.chuqabp.course.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.users.CreateUser
import org.sic4change.usescases.users.ForgotPassword
import org.sic4change.usescases.users.GetSavedUser
import org.sic4change.usescases.users.Login

class LoginViewModel(private val login: Login, private val forgotPassword: ForgotPassword,
                     private val createUser: CreateUser, private val getSavedUser: GetSavedUser,
                     uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _showingLoginErrors = MutableLiveData<Event<String>>()
    val showingLoginErrors: LiveData<Event<String>> get() = _showingLoginErrors

    private val _showingCreateAccountErrors = MutableLiveData<Event<String>>()
    val showingCreateAccountErrors: LiveData<Event<String>> get() = _showingCreateAccountErrors

    private val _showingForgotPasswordResult = MutableLiveData<Event<Boolean>>()
    val showingForgotPasswordResult: LiveData<Event<Boolean>> get() = _showingForgotPasswordResult

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain: LiveData<Event<Unit>> get() = _navigateToMain

    private val _navigateToCreateAccount = MutableLiveData<Event<Unit>>()
    val navigateToCreateAccount: LiveData<Event<Unit>> get() = _navigateToCreateAccount

    private val _showingLoginView = MutableLiveData<Boolean>()
    val showingLoginView: LiveData<Boolean> get() = _showingLoginView

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            val user = getSavedUser.invoke()
            if (user != null) _navigateToMain.value = Event(Unit) else _showingLoginView.value = true
        }
    }

    fun onLoginClicked(email: String, password: String) {
        launch {
            _loading.value = true
            val result = login.invoke(email, password)
            val user = getSavedUser.invoke()
            if (user != null) _navigateToMain.value = Event(Unit)
            else _showingLoginErrors.value = Event(result)
            _loading.value = false
        }
    }

    fun onCreateAccountClicked() {
        _navigateToCreateAccount.value = Event(Unit)
    }

    fun onForgotPasswordClicked(email: String) {
        launch {
            _loading.value = true
            if (forgotPassword.invoke(email)) {
                _showingForgotPasswordResult.value = Event(true)
            }
            else {
                _showingForgotPasswordResult.value = Event(false)
            }
            _loading.value = false
        }
    }

    fun onCreateNewUserClicked(email: String, password: String) {
        launch {
            _loading.value = true
            val result = createUser.invoke(email, password)
            val user = getSavedUser.invoke()
            if (user != null) {
                _navigateToMain.value = Event(Unit)
            }
            else{
                _showingCreateAccountErrors.value = Event(result)
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}