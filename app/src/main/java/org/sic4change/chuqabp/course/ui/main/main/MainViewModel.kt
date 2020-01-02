package org.sic4change.chuqabp.course.ui.main.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.sic4change.domain.Case
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.GetCases

class MainViewModel(private val getCases: GetCases) : ScopedViewModel() {

    private val _cases = MutableLiveData<List<Case>>()
    val cases: LiveData<List<Case>> get() = _cases

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToCase = MutableLiveData<Event<String>>()
    val navigateToCase: LiveData<Event<String>> get() = _navigateToCase

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequest() {
        launch {
            _loading.value = true
            _cases.value = getCases.invoke()
            _loading.value = false
        }
    }

    fun onCaseClicked(case: Case) {
        _navigateToCase.value = Event(case.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}
