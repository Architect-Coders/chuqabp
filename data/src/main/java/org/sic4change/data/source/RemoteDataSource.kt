package org.sic4change.data.source

import org.sic4change.domain.Case
import org.sic4change.domain.User

interface RemoteDataSource {
    suspend fun getCases(mentorId: String?) : List<Case>
    suspend fun createCase(user: User?, case: Case)
    suspend fun updateCase(user: User?, case: Case)
    suspend fun deleteCase(id: String)
    suspend fun getUser(email: String) : User
    suspend fun login(email: String, password: String) : String
    suspend fun forgotPassword(email: String) : Boolean
    suspend fun changePassword(email: String)
    suspend fun createUser(email: String, password: String) : String


}