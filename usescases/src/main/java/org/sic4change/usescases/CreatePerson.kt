package org.sic4change.usescases

import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Person

class CreatePerson (private val casesRepository: PersonsRepository) {

    suspend fun invoke(person: Person) = casesRepository.createPerson(person)

}