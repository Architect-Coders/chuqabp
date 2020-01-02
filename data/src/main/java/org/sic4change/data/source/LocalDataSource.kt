package org.sic4change.data.source

import org.sic4change.domain.Case

interface LocalDataSource {
    suspend fun deleteCases()
    suspend fun insertCases(cases: List<Case>)
    suspend fun getCases() : List<Case>
    suspend fun findById(id: String) : Case

}