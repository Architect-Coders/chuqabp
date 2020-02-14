package org.sic4change.chuqabp.course.ui.main.user

import kotlinx.coroutines.CoroutineDispatcher
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel

class UserViewModel (uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {


    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}