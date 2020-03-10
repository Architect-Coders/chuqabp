package org.sic4change.usescases.resources

import org.sic4change.data.repository.ResourcesRepository
import org.sic4change.domain.Resource

class GetResources(private val resourcesRepository: ResourcesRepository) {

    suspend fun invoke() : List<Resource> = resourcesRepository.getResources()


}