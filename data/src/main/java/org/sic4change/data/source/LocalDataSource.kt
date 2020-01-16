package org.sic4change.data.source

import org.sic4change.domain.Case
import org.sic4change.domain.User

interface LocalDataSource {
    suspend fun getUser() : User?
    suspend fun createCase(case: Case)
    suspend fun updateCase(case: Case)
    suspend fun deleteCase(id: String)
    suspend fun getUser(id: String) : User?
    suspend fun insertUser(user: User)
    suspend fun deleteUser()
    suspend fun deleteCases()
    suspend fun insertCases(cases: List<Case>)
    suspend fun getCases() : List<Case>
    suspend fun findById(id: String) : Case

}