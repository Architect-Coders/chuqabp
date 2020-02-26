package org.sic4change.usescases

import org.sic4change.data.repository.CasesRepository

class DeleteCase (private val casesRepository: CasesRepository) {

    suspend fun invoke(id: String) = casesRepository.deleteCase(id)

}