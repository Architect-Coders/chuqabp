package org.sic4change.chuqabp.course.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.common.Scope

class MainViewModel(private val casesRepository: CasesRepository) : ViewModel(),  Scope by Scope.Impl() {

    sealed class UIModel {
        object Loading: UIModel()
        class Content(val cases: List<Case>) : UIModel()
        class Navigation(val case: Case) : UIModel()
        object RequestLocationPermission : UIModel()
    }

    private val _model = MutableLiveData<UIModel>()

    val model : LiveData<UIModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UIModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequest() {
        launch {
            _model.value = UIModel.Loading
            _model.value = UIModel.Content(casesRepository.getCases())
            casesRepository.refresh()
            _model.value = UIModel.Content(casesRepository.getCases())
        }
    }

    fun onCaseClicked(case: Case) {
        _model.value = UIModel.Navigation(case)
    }

    override fun onCleared() {
        destroyScope()
    }

}
