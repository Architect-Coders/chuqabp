package org.sic4change.chuqabp.course.ui.main.newcase


import kotlinx.coroutines.CoroutineDispatcher
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel

class NewCaseViewModel
    (uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {



    init {
        initScope()
    }



    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}