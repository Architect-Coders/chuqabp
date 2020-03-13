package org.sic4change.chuqabp.course.ui.main.newcase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Person
import org.sic4change.domain.Case
import org.sic4change.domain.Resource
import org.sic4change.usescases.cases.CreateCase
import org.sic4change.usescases.locations.GetLocation
import org.sic4change.usescases.persons.FindPersonById
import org.sic4change.usescases.persons.GetPersonsToSelect
import org.sic4change.usescases.resources.GetResources
import java.text.SimpleDateFormat
import java.util.*

class NewCaseViewModel (private val personId: String, private val findPersonById: FindPersonById,
                        private val getPersons: GetPersonsToSelect, private val getLocation: GetLocation,
                        private val createCase: CreateCase, private val getResources: GetResources,
                        uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _resources = MutableLiveData<List<Resource>>()
    val resources: LiveData<List<Resource>> get() = _resources

    private val _resourcesSelected = MutableLiveData<String>()
    val resourceSelected: LiveData<String> get() = _resourcesSelected

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
            _resourcesSelected.value = ""
            _currentLocation.value = getLocation.invoke()
            _persons.value = getPersons.invoke()
            _resources.value = getResources.invoke()
            try {
                _person.value = findPersonById.invoke(personId)
            } catch (e:Exception) { }
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

    fun onResourceClicked(resourceClicked: Resource) {
        if (!_resourcesSelected.value.isNullOrEmpty()) {
            for (resource: String in _resourcesSelected.value?.split(",")!!) {
                if (resource == resourceClicked.id) {
                    _resourcesSelected.value = _resourcesSelected.value!!.replace("$resource,", "")
                    _resourcesSelected.value = _resourcesSelected.value!!.replace(resource , "")
                    if (_resourcesSelected.value!!.isNotEmpty() && _resourcesSelected.value!!.last() == ',') {
                        _resourcesSelected.value = _resourcesSelected.value!!.substring(0, _resourcesSelected.value!!.length -1)
                    }
                    _resources.value?.find { it.id ==  resourceClicked.id}?.selected = false
                    return
                }
            }
            if (_resourcesSelected.value == resourceClicked.id) {
                _resourcesSelected.value = ""
                _resources.value?.find { it.id ==  resourceClicked.id}?.selected = false
            } else {
                _resourcesSelected.value = _resourcesSelected.value + "," + resourceClicked.id
                _resources.value?.find { it.id ==  resourceClicked.id}?.selected = true
            }
        } else {
            _resources.value?.find { it.id ==  resourceClicked.id}?.selected = true
            _resourcesSelected.value = resourceClicked.id
        }
    }

    fun onRegisterCaseClicked(person: String, name: String, surnames: String, date: String, hour: String, place: String,
                              physical: Boolean, sexual: Boolean, psycological: Boolean,
                              social: Boolean, economic: Boolean, description: String) {
        launch {
            val dateformatddMMyyyy = SimpleDateFormat("dd/MM/yyyy")
            val dateChanged = dateformatddMMyyyy.parse(date)
            createCase.invoke(Case(Date().time.toString() + person, person, name, surnames, dateChanged.time, hour, place,
                physical, sexual, psycological, social, economic, description, _resourcesSelected.value.toString(),
                "open", "", "", ""
            ))
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}