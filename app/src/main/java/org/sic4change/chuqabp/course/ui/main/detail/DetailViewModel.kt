package org.sic4change.chuqabp.course.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.domain.Person
import org.sic4change.chuqabp.course.ui.common.ScopedViewModel
import org.sic4change.domain.Case
import org.sic4change.usescases.FindPersonById
import org.sic4change.usescases.DeletePerson
import org.sic4change.usescases.GetCasesPerson


class DetailViewModel(private val personId: String, private val findPersonById: FindPersonById,
                      private val deletePerson: DeletePerson, private val getCasesPerson: GetCasesPerson,
                      uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    private val _casesPerson = MutableLiveData<List<Case>>()
    val casesPerson: LiveData<List<Case>> get() = _casesPerson

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> get() = _deleted

    private val _navigateToCase = MutableLiveData<Event<String>>()
    val navigateToCase: LiveData<Event<String>> get() = _navigateToCase


    init {
        launch {
            _deleted.value = false
            _person.value = findPersonById.invoke(personId)
            _casesPerson.value = getCasesPerson.invoke(personId)
            updateUI()
        }
    }

    private fun updateUI() {
        person.value?.run {
            _title.value = "$name $surnames"
            _url.value = photo
        }
    }

    fun findPersonById() {
        launch {
            _person.value = findPersonById.invoke(personId)
        }
    }

    fun deletePerson() {
        launch {
            _deleted.value = true
            deletePerson.invoke(personId)
        }
    }

    fun onCaseClicked(case: Case) {
        _navigateToCase.value = Event(case.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}
