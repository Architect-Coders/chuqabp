package org.sic4change.usescases.persons

import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Person

class UpdatePerson (private val personsRepository: PersonsRepository) {

    suspend fun invoke(case: Person) = personsRepository.updatePerson(case)

}