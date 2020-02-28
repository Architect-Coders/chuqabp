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


}



