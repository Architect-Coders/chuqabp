package org.sic4change.usescases

import org.sic4change.data.repository.PersonsRepository

class DeletePerson (private val personsRepository: PersonsRepository) {

    suspend fun invoke(id: String) = personsRepository.deletePerson(id)

}