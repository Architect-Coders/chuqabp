package org.sic4change.chuqabp.course.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel


class DetailViewModel(private val caseId: String, private val casesRepository: CasesRepository) : ScopedViewModel(){

    class UIModel(val case: Case)

    private val _model = MutableLiveData<UIModel>()

    val model : LiveData<UIModel>
        get() {
            if (_model.value == null) findCase()
            return _model
        }

    private fun findCase() = launch {
        _model.value = UIModel(casesRepository.findCaseById(caseId))
    }
}
