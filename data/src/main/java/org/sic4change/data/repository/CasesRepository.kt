package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.Case

class CasesRepository(private val localDataSource: LocalDataSource,
                      private val remoteDataSource: RemoteDataSource) {

    suspend fun getCases() : List<Case> {
        val cases = remoteDataSource.getCases()
        localDataSource.deleteCases()
        localDataSource.insertCases(cases)
        return localDataSource.getCases()
    }

    suspend fun findCaseById(id: String): Case = localDataSource.findById(id)

}



