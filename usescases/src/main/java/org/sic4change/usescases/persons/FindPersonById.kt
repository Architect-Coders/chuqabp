package org.sic4change.usescases.persons

import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Person

class FindPersonById(private val personsRepository : PersonsRepository) {

    suspend fun invoke(id: String) : Person = personsRepository.findPersonById(id)
}