package org.sic4change.chuqabp.course.ui.main.newcase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Case
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetLocation
import java.util.*

class NewCaseViewModel (private val getLocation: GetLocation, private val createCase: CreateCase,
                        uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _showingCreateCaseError = MutableLiveData<Event<Boolean>>()
    val showingCreateCaseError: LiveData<Event<Boolean>> get() = _showingCreateCaseError

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation

    private val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> get() = _photoUrl

    init {
        initScope()
        getLocation()
    }

    fun getLocation() {
        launch {
            _currentLocation.value = getLocation.invoke()
        }
    }

    fun onCreateCaseClicked(name: String, surnames: String, date: String, phone: String, email: String, photo: String,
                            location: String) {
        launch {
            if (name.isEmpty() || surnames.isEmpty() || location.isEmpty() || date.isEmpty()) {
                _showingCreateCaseError.value = Event(true)
            } else {
                _showingCreateCaseError.value = Event(false)
                createCase.invoke(Case(Date().time.toString() + phone, name, surnames, date, phone, email, photo, location))
            }
        }
    }

    fun setPhotoUrl(photoUrl: String) {
        _photoUrl.value = photoUrl
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}