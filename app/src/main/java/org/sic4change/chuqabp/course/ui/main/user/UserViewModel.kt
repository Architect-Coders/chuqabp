package org.sic4change.chuqabp.course.ui.main.user

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.ChangePassword
import org.sic4change.usescases.Logout

class UserViewModel (private val changePassword: ChangePassword, private val logout: Logout,
                     uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

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
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}