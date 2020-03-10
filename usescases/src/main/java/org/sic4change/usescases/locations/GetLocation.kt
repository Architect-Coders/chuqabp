package org.sic4change.usescases.locations

import org.sic4change.data.repository.RegionRepository

class GetLocation(private val regionRepository: RegionRepository) {

    suspend fun invoke() : String = regionRepository.findLastRegion()
}