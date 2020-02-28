package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.Resource

class ResourcesRepository (private val localDataSource: LocalDataSource,
                           private val remoteDataSource: RemoteDataSource) {

    suspend fun getResources() : List<Resource> {
        val resources = remoteDataSource.getResources()
        localDataSource.deleteResources()
        localDataSource.insertResources(resources)
        return localDataSource.getResources()
    }

    suspend fun getCaseResources(resources: String) : List<Resource> {
        var listResources = emptyList<Resource>().toMutableList()
        if (!resources.isNullOrEmpty()) {
            if (!resources.contains(",")) {
                var resourceToAdd = localDataSource.findResourceById(resources)
                resourceToAdd.selected = true
                listResources.add(resourceToAdd)
            } else {
                for (resourceSplitted: String in resources.split(",")) {
                    var resourceToAdd = localDataSource.findResourceById(resourceSplitted)
                    resourceToAdd.selected = true
                    listResources.add(resourceToAdd)
                }
            }
        }
        return listResources.toList()
    }


}



