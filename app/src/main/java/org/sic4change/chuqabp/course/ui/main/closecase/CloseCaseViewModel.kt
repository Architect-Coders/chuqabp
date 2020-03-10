package org.sic4change.chuqabp.course.ui.main.closecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Case
import org.sic4change.domain.ClosedReason
import org.sic4change.usescases.cases.FindCaseById
import org.sic4change.usescases.cases.UpdateCase
import org.sic4change.usescases.reasons.GetClosedReasons
import java.text.SimpleDateFormat
import java.util.*

class CloseCaseViewModel(private val caseId: String, private val findCaseById: FindCaseById,
                         private val updateCase: UpdateCase, getClosedReasons: GetClosedReasons,
                         uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _case = MutableLiveData<Case>()
    val case: LiveData<Case> get() = _case

    private val _closeReasons = MutableLiveData<List<ClosedReason>>()
    val closeReasons: LiveData<List<ClosedReason>> get() = _closeReasons

    private val _closeReasonSelected = MutableLiveData<String>()
    val closeReasonSelected: LiveData<String> get() = _closeReasonSelected

    private val _description =  MutableLiveData<String>()
    val description: LiveData<String> get() =  _description

    init {
        launch {
            _case.value = findCaseById.invoke(caseId)
            if (_case.value != null) {
                _closeReasons.value = getClosedReasons.invoke()
            }
        }
    }

    fun onClosedReasonClicked(reasonClicked: ClosedReason) {
        if (_closeReasons.value != null) {
            for (closeReason : ClosedReason in _closeReasons.value!!) {
                closeReason.selected = closeReason.id == reasonClicked.id
            }
        }
        _closeReasonSelected.value = reasonClicked.name
    }

    fun onCloseCaseClicked(description: String) {
        launch{
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            launch{
                updateCase.invoke(Case(
                    _case.value!!.id, _case.value!!.person, _case.value!!.name, _case.value!!.surnames,
                    _case.value!!.date, _case.value!!.hour, _case.value!!.place, _case.value!!.physic, _case.value!!.sexual,
                    _case.value!!.psychologic, _case.value!!.social, _case.value!!.economic, _case.value!!.description, _case.value!!.resources,
                    "closed", description, _closeReasonSelected.value.toString(), currentDate))
            }
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}