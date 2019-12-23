package org.sic4change.chuqabp.course.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel


class DetailViewModel(private val caseId: String, private val casesRepository: CasesRepository) : ScopedViewModel() {

    private val _case = MutableLiveData<Case>()
    val case: LiveData<Case> get() = _case

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url


    init {
        launch {
            _case.value = casesRepository.findCaseById(caseId)
            updateUI()
        }
    }

    private fun updateUI() {
        case.value?.run {
            _title.value = "$name $surnames"
            _url.value = photo
        }
    }
}
