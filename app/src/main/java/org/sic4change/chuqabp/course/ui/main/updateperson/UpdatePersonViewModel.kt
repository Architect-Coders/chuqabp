package org.sic4change.chuqabp.course.ui.main.updateperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.domain.Person
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.persons.FindPersonById
import org.sic4change.usescases.persons.UpdatePerson


class UpdatePersonViewModel(private val personId: String, private val findPersonById: FindPersonById,
                            private val updatePerson: UpdatePerson,
                            uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _showingUpdatePersonError = MutableLiveData<Event<Boolean>>()
    val showingUpdatePersonError: LiveData<Event<Boolean>> get() = _showingUpdatePersonError

    private val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> get() = _photoUrl

    init {
        launch {
            _person.value = findPersonById.invoke(personId)
            _photoUrl.value = person.value?.photo.toString()
        }
    }

    fun setPhotoUrl(photoUrl: String) {
        _photoUrl.value = photoUrl
    }

    fun findPersonById() {
        launch {
            _person.value = findPersonById.invoke(personId)
        }
    }


    fun onUpdatePersonClicked(id: String, name: String, surnames: String, date: String, phone: String, email: String, photo: String,
                              location: String) {
        launch {
            if (name.isEmpty() || surnames.isEmpty() || location.isEmpty() || date.isEmpty()) {
                _showingUpdatePersonError.value = Event(true)
            } else {
                _showingUpdatePersonError.value = Event(false)
                updatePerson.invoke(Person(id, name, surnames, date, phone, email, photo, location))
            }
        }
    }


}
