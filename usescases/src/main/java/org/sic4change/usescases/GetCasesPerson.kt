package org.sic4change.usescases

import org.sic4change.data.repository.CasesRepository
import org.sic4change.data.repository.PersonsRepository
import org.sic4change.domain.Case

class GetCasesPerson(private val personsRepository: PersonsRepository) {

    suspend fun invoke(person: String) : List<Case> = personsRepository.getCasesPerson(person)


}