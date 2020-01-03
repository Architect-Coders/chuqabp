package org.sic4change.data.source

import org.sic4change.domain.Case
import org.sic4change.domain.User

interface RemoteDataSource {
    suspend fun getCases() : List<Case>
    suspend fun getUser(email: String) : User
    suspend fun login(email: String, password: String) : String
    suspend fun forgotPassword(email: String) : Boolean
    suspend fun createUser(email: String, password: String) : String


}