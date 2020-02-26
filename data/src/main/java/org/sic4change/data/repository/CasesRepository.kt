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


}



