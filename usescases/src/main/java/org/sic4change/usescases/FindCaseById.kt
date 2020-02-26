package org.sic4change.usescases

import org.sic4change.data.repository.CasesRepository
import org.sic4change.domain.Case

class FindCaseById(private val casesRepository: CasesRepository) {

    suspend fun invoke(id: String) : Case = casesRepository.findCaseById(id)
}