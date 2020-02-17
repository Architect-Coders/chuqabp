package org.sic4change.chuqabp.course.ui.main.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.ChangePassword
import org.sic4change.usescases.DeleteUser
import org.sic4change.usescases.Logout

class UserViewModel (private val changePassword: ChangePassword, private val logout: Logout,
                     private val deleteUser: DeleteUser, uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _logoutConfirmed = MutableLiveData<Event<Boolean>>()
    val logoutConfirmed: LiveData<Event<Boolean>> get() = _logoutConfirmed

    private val _removeAccountConfirmed = MutableLiveData<Event<Boolean>>()
    val removeAccountConfirmed: LiveData<Event<Boolean>> get() = _removeAccountConfirmed

    init {
        initScope()
    }

    fun onChangePasswordClicked() {
        launch {
            changePassword.invoke()
        }
    }

    fun onLogoutClicked() {
        launch {
            logout.invoke()
            _logoutConfirmed.value = Event(true)
        }
    }

    fun onDeleteUserClicked() {
        launch {
            deleteUser.invoke()
            _removeAccountConfirmed.value = Event(true)
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}