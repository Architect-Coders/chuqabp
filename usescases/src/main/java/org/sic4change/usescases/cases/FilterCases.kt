package org.sic4change.usescases.cases

import org.sic4change.data.repository.CasesRepository
import org.sic4change.domain.Case

class FilterCases(private val casesRepository: CasesRepository) {

    suspend fun invoke(nameSurnames: String, place: String, physic: Boolean?, sexual: Boolean?,
                       psychologic: Boolean?, social: Boolean?, economic: Boolean?, status: String?) : List<Case> =
        casesRepository.filterCases(nameSurnames, place, physic, sexual, psychologic, social, economic, status)


}