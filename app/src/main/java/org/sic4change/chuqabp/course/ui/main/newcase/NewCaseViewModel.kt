package org.sic4change.chuqabp.course.ui.main.newcase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Person
import org.sic4change.domain.Case
import org.sic4change.domain.Resource
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetLocation
import org.sic4change.usescases.GetPersonsToSelect
import org.sic4change.usescases.GetResources
import java.util.*

class NewCaseViewModel (private val getPersons: GetPersonsToSelect, private val getLocation: GetLocation,
                        private val createCase: CreateCase, private val getResources: GetResources,
                        uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _resources = MutableLiveData<List<Resource>>()
    val resources: LiveData<List<Resource>> get() = _resources

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private val _hour = MutableLiveData<String>()
    val hour: LiveData<String> get() = _hour

    private val _type =  MutableLiveData<Boolean>()
    val type: LiveData<Boolean> get() =  _type

    init {
        initScope()
        launch {
            _currentLocation.value = getLocation.invoke()
            _persons.value = getPersons.invoke()
            _resources.value = getResources.invoke()
            println("Aqui")
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
        _person.value = person
    }

    fun onResourceClicked(resource: Resource) {

    }

    fun onRegisterCaseClicked(person: String?, name: String?, surnames: String?, date: String, hour: String, place: String,
                              physical: Boolean, sexual: Boolean, psycological: Boolean,
                              social: Boolean, economic: Boolean, description: String) {
        launch {
            person?.let {
                name?.let { it1 ->
                    surnames?.let { it2 ->
                        Case(Date().time.toString() + person, it, it1, it2, date, hour, place,
                            physical, sexual, psycological, social, economic, description)
                    }
                }
            }?.let { createCase.invoke(it) }
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}