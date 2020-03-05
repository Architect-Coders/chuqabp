package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.ClosedReason

class ClosedReasonsRepository (private val localDataSource: LocalDataSource,
                               private val remoteDataSource: RemoteDataSource) {

    suspend fun getClosedReasons() : List<ClosedReason> {
        val closedReason = remoteDataSource.getClosedReasons()
        localDataSource.deleteClosedReasons()
        localDataSource.insertClosedReasons(closedReason)
        return localDataSource.getClosedReasons()
    }



}



