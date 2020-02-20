package org.sic4change.chuqabp.course.ui.main.newcase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Person
import org.sic4change.usescases.GetLocation
import org.sic4change.usescases.GetPersonsToSelect
import java.util.*

class NewCaseViewModel (private val getPersons: GetPersonsToSelect, private val getLocation: GetLocation,
                        uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date> get() = _date

    init {
        initScope()
        launch {
            _currentLocation.value = getLocation.invoke()
            _persons.value = getPersons.invoke()
        }
    }

    fun onPersonClicked(person: Person) {
        _person.value = person
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}