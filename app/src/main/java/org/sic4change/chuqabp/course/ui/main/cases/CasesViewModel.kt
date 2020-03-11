package org.sic4change.chuqabp.course.ui.main.cases


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Case
import org.sic4change.usescases.cases.FilterCases
import org.sic4change.usescases.cases.GetCases
import org.sic4change.usescases.cases.RefreshCases

class CasesViewModel (private val getCases: GetCases, private val refreshCases: RefreshCases,
                      private val filterCases: FilterCases, uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _cases = MutableLiveData<List<Case>>()
    val cases: LiveData<List<Case>> get() = _cases

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToCase = MutableLiveData<Event<String>>()
    val navigateToCase: LiveData<Event<String>> get() = _navigateToCase

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            _loading.value = true
            _cases.value = getCases.invoke()
            _loading.value = false
        }
    }

    fun refreshCases() {
        launch {
            _loading.value = true
            _cases.value = refreshCases.invoke()
            _loading.value = false
        }
    }

    fun filterCases(nameSurnames: String, place: String) {
        launch {
            _loading.value = true
            _cases.value = emptyList()
            _cases.value = filterCases.invoke(nameSurnames, place)
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