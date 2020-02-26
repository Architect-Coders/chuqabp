package org.sic4change.chuqabp.course.ui.main.updatecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Case
import org.sic4change.domain.Person
import org.sic4change.usescases.*

class UpdateCaseViewModel(private val caseId: String, private val findCaseById: FindCaseById,
                            private val updateCase: UpdateCase, private val getLocation: GetLocation,
                            private val getPersonsToSelect: GetPersonsToSelect, private val findPersonById: FindPersonById,
                          uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _case = MutableLiveData<Case>()
    val case: LiveData<Case> get() = _case

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private val _hour = MutableLiveData<String>()
    val hour: LiveData<String> get() = _hour

    private val _type =  MutableLiveData<Boolean>()
    val type: LiveData<Boolean> get() =  _type

    private val _description =  MutableLiveData<String>()
    val description: LiveData<String> get() =  _description

    private val _personSelected =  MutableLiveData<Boolean>()
    val personSelected: LiveData<Boolean> get() =  _personSelected

    init {
        launch {
            _case.value = findCaseById.invoke(caseId)
            if (_case.value != null) {
                _person.value = findPersonById.invoke(_case.value!!.person)
                _date.value = _case.value!!.date
                _hour.value = _case.value!!.hour
                _currentLocation.value = _case.value!!.place
                _description.value = _case.value!!.description
            }

            _persons.value = getPersonsToSelect.invoke()
        }
    }

    fun setDate(date: String) {
        _date.value = date
    }

    fun setHour(hour: String) {
        _hour.value = hour
    }

    fun setType(type: Boolean) {
        _type.value = type
    }

    fun onPersonClicked(person: Person) {
        _personSelected.value = true
        _person.value = person
    }

    fun setPersonSelected(selected: Boolean) {
        _personSelected.value = selected
    }

    fun onUpdateCaseClicked(id: String?, person: String?, name: String?, surnames: String?, date: String,
        hour: String, place: String, physic: Boolean, sexual: Boolean, psycological: Boolean, social: Boolean,
        economic: Boolean, description: String) {
        launch {
            id?.let {
                person?.let { it1 ->
                    name?.let { it2 ->
                        surnames?.let { it3 ->
                            Case(
                                it, it1, it2, it3, date, hour, place, physic, sexual, psycological,
                                social, economic, description
                            )
                        }
                    }
                }
            }?.let {
                updateCase.invoke(
                    it
                )
            }
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}