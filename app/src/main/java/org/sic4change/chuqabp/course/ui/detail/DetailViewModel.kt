package org.sic4change.chuqabp.course.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sic4change.chuqabp.course.model.Case


class DetailViewModel(private val case: Case) : ViewModel(){

    class UIModel(val case: Case)

    private val _model = MutableLiveData<UIModel>()

    val model : LiveData<UIModel>
        get() {
            if (_model.value == null) _model.value = UIModel(case)
            return _model
        }
}
