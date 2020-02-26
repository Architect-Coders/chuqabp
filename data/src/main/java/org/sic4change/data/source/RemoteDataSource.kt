package org.sic4change.data.source

import org.sic4change.domain.Person
import org.sic4change.domain.User
import org.sic4change.domain.Case

interface RemoteDataSource {
    suspend fun getUser(email: String) : User
    suspend fun login(email: String, password: String) : String
    suspend fun logout()
    suspend fun deleteUser(id: String)
    suspend fun forgotPassword(email: String) : Boolean
    suspend fun changePassword(email: String)
    suspend fun createUser(email: String, password: String) : String
    suspend fun getPersons(mentorId: String?) : List<Person>
    suspend fun createPerson(user: User?, person: Person)
    suspend fun updatePerson(user: User?, person: Person)
    suspend fun deletePerson(id: String)
    suspend fun createCase(user: User?, case: Case)
    suspend fun getCases(mentorId: String?) : List<Case>
    suspend fun deleteCase(id: String)


}