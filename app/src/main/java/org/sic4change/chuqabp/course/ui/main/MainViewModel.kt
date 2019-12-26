package org.sic4change.chuqabp.course.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.Scope

class MainViewModel(private val casesRepository: CasesRepository) : ViewModel(),  Scope by Scope.Impl() {

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
            casesRepository.refresh()
            _cases.value = casesRepository.getCases()
            _loading.value = false
        }
    }

    fun onCaseClicked(case: Case) {
        _navigateToCase.value = Event(case.id)
    }

    override fun onCleared() {
        destroyScope()
    }

}
