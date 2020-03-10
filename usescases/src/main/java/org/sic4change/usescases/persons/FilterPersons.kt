package org.sic4change.usescases.persons

import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Person

class FilterPersons(private val personsRepository: PersonsRepository) {

    suspend fun invoke(nameSurnames: String, location: String) : List<Person> = personsRepository.filterPersons(nameSurnames, location)


}