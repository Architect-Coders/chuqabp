package org.sic4change.usescases

import org.sic4change.data.repository.ResourcesRepository
import org.sic4change.domain.Resource

class GetResourcesCase(private val resourcesRepository: ResourcesRepository) {

    suspend fun invoke(resources: String) : List<Resource> = resourcesRepository.getCaseResources(resources)


}