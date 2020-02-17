package org.sic4change.usescases

import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Person

class GetPersons(private val personsRepository: PersonsRepository) {

    suspend fun invoke() : List<Person> = personsRepository.getPersons()


}