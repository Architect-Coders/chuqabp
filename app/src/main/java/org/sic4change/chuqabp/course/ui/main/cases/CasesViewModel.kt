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

    private val _cpPhysicSelected = MutableLiveData<Boolean>()
    val cpPhysicSelected: LiveData<Boolean> get() = _cpPhysicSelected

    private val _cpSexualSelected = MutableLiveData<Boolean>()
    val cpSexualSelected: LiveData<Boolean> get() = _cpSexualSelected

    private val _cpPsicologicalSelected = MutableLiveData<Boolean>()
    val cpPsicologicalSelected: LiveData<Boolean> get() = _cpPsicologicalSelected

    private val _cpSocialSelected = MutableLiveData<Boolean>()
    val cpSocialSelected: LiveData<Boolean> get() = _cpSocialSelected

    private val _cpEconomicSelected = MutableLiveData<Boolean>()
    val cpEconomicSelected: LiveData<Boolean> get() = _cpEconomicSelected

    private val _statusSelected = MutableLiveData<String>()
    val statusSelected: LiveData<String> get() = _statusSelected

    fun selectUnSelectPhysic() {
        _cpPhysicSelected.value = _cpPhysicSelected.value != true
    }

    fun selectUnSelectSexual() {
        _cpSexualSelected.value = _cpSexualSelected.value != true
    }

    fun selectUnSelectPsicological() {
        _cpPsicologicalSelected.value = _cpPsicologicalSelected.value != true
    }

    fun selectUnSelectEconomic() {
        _cpEconomicSelected.value = _cpEconomicSelected.value != true
    }

    fun selectUnSelectSocial() {
        _cpSocialSelected.value = _cpSocialSelected.value != true
    }

    fun selectStatus(status: String) {
        _statusSelected.value = status
    }

    fun resetTypesSelected() {
        _cpPhysicSelected.value = false
        _cpSexualSelected.value = false
        _cpPsicologicalSelected.value = false
        _cpEconomicSelected.value = false
        _cpSocialSelected.value = false
        _statusSelected.value = ""
    }

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
            _cases.value = filterCases.invoke(nameSurnames, place, _cpPhysicSelected.value,
                _cpSexualSelected.value, _cpPsicologicalSelected.value, _cpSocialSelected.value,
                _cpEconomicSelected.value, _statusSelected.value)
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