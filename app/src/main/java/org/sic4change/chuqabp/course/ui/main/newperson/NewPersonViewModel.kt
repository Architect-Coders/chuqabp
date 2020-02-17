package org.sic4change.chuqabp.course.ui.main.newperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Person
import org.sic4change.usescases.CreatePerson
import org.sic4change.usescases.GetLocation
import java.util.*

class NewPersonViewModel (private val getLocation: GetLocation, private val createPerson: CreatePerson,
                          uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _showingCreatePersonError = MutableLiveData<Event<Boolean>>()
    val showingCreatePersonError: LiveData<Event<Boolean>> get() = _showingCreatePersonError

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

    fun onCreatePersonClicked(name: String, surnames: String, date: String, phone: String, email: String, photo: String,
                              location: String) {
        launch {
            if (name.isEmpty() || surnames.isEmpty() || location.isEmpty() || date.isEmpty()) {
                _showingCreatePersonError.value = Event(true)
            } else {
                _showingCreatePersonError.value = Event(false)
                createPerson.invoke(Person(Date().time.toString() + phone, name, surnames, date, phone, email, photo, location))
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