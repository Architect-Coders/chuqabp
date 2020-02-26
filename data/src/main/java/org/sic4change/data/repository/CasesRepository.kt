package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.Case

class CasesRepository(private val localDataSource: LocalDataSource,
                      private val remoteDataSource: RemoteDataSource) {

    suspend fun createCase(case: Case) {
        val user = localDataSource.getUser()
        localDataSource.createCase(case)
        remoteDataSource.createCase(user, case)
    }

    suspend fun refreshCases() : List<Case> {
        val user = localDataSource.getUser()
        val cases = remoteDataSource.getCases(user?.id)
        localDataSource.deleteCases()
        localDataSource.insertCases(cases)
        return localDataSource.getCases()
    }

    suspend fun getCases() : List<Case> {
        if (localDataSource.getCases().isEmpty()) {
            val user = localDataSource.getUser()
            val cases = remoteDataSource.getCases(user?.id)
            localDataSource.deleteCases()
            localDataSource.insertCases(cases)
        }
        return localDataSource.getCases()
    }


}



