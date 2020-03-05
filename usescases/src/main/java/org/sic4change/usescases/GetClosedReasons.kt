package org.sic4change.usescases

import org.sic4change.data.repository.ClosedReasonsRepository
import org.sic4change.domain.ClosedReason

class GetClosedReasons(private val resourcesRepository: ClosedReasonsRepository) {

    suspend fun invoke() : List<ClosedReason> = resourcesRepository.getClosedReasons()


}