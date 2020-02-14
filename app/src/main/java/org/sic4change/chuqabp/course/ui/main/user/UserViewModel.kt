package org.sic4change.chuqabp.course.ui.main.user

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.ChangePassword

class UserViewModel (private val changePassword: ChangePassword, uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {


    init {
        initScope()
    }

    fun onChangePasswordClicked() {
        launch {
            changePassword.invoke()
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}