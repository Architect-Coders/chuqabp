package org.sic4change.chuqabp.course.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.sic4change.domain.Case
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.FindCaseById
import org.sic4change.usescases.DeleteCase


class DetailViewModel(private val caseId: String, private val findCaseById: FindCaseById,
                      private val deleteCase: DeleteCase) : ScopedViewModel() {

    private val _case = MutableLiveData<Case>()
    val case: LiveData<Case> get() = _case

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> get() = _deleted


    init {
        launch {
            _deleted.value = false
            _case.value = findCaseById.invoke(caseId)
            updateUI()
        }
    }

    private fun updateUI() {
        case.value?.run {
            _title.value = "$name $surnames"
            _url.value = photo
        }
    }

    fun deleteCase() {
        launch {
            _deleted.value = true
            deleteCase.invoke(caseId)
        }
    }

}
