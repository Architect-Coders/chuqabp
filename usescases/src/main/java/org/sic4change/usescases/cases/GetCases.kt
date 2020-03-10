package org.sic4change.usescases.cases

import org.sic4change.data.repository.CasesRepository
import org.sic4change.domain.Case

class GetCases(private val casesRepository: CasesRepository) {

    suspend fun invoke() : List<Case> = casesRepository.getCases()


}