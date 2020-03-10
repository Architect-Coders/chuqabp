package org.sic4change.chuqabp.course.ui.main.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.domain.Person
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.usescases.persons.FilterPersons
import org.sic4change.usescases.persons.GetPersons
import org.sic4change.usescases.persons.RefreshPersons

class MainViewModel(private val getPersons: GetPersons, private val refreshPersons: RefreshPersons,
                    private val filterPersons: FilterPersons,
                    uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToPerson = MutableLiveData<Event<String>>()
    val navigateToPerson: LiveData<Event<String>> get() = _navigateToPerson

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequest() {
        launch {
            _loading.value = true
            _persons.value = getPersons.invoke()
            _loading.value = false
        }
    }

    fun refreshPersons() {
        launch {
            _loading.value = true
            _persons.value = refreshPersons.invoke()
            _loading.value = false
        }
    }

    fun filterPersons(nameSurnames: String, location: String) {
        launch {
            _loading.value = true
            _persons.value = emptyList()
            _persons.value = filterPersons.invoke(nameSurnames, location)
            _loading.value = false
        }
    }

    fun onPersonClicked(person: Person) {
        _navigateToPerson.value = Event(person.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}
