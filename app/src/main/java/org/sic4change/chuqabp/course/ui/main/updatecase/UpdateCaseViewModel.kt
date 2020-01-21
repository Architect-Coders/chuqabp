package org.sic4change.chuqabp.course.ui.main.updatecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.domain.Case
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.FindCaseById
import org.sic4change.usescases.UpdateCase


class UpdateCaseViewModel(private val caseId: String, private val findCaseById: FindCaseById, private val updateCase: UpdateCase) : ScopedViewModel() {

    private val _case = MutableLiveData<Case>()
    val case: LiveData<Case> get() = _case

    private val _showingUpdateCaseError = MutableLiveData<Event<Boolean>>()
    val showingUpdateCaseError: LiveData<Event<Boolean>> get() = _showingUpdateCaseError

    private val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> get() = _photoUrl

    init {
        launch {
            _case.value = findCaseById.invoke(caseId)
            _photoUrl.value = case.value?.photo.toString()
        }
    }

    fun setPhotoUrl(photoUrl: String) {
        _photoUrl.value = photoUrl
    }

    fun onUpdateCaseClicked(id: String, name: String, surnames: String, date: String, phone: String, email: String, photo: String,
                            location: String) {
        launch {
            if (name.isEmpty() || surnames.isEmpty() || location.isEmpty() || date.isEmpty()) {
                _showingUpdateCaseError.value = Event(true)
            } else {
                _showingUpdateCaseError.value = Event(false)
                updateCase.invoke(Case(id, name, surnames, date, phone, email, photo, location))
            }
        }
    }


}
