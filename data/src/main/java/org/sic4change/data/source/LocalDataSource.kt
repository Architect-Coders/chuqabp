package org.sic4change.data.source

import org.sic4change.domain.Person
import org.sic4change.domain.User
import org.sic4change.domain.Case

interface LocalDataSource {
    suspend fun getUser() : User?
    suspend fun getUser(id: String) : User?
    suspend fun insertUser(user: User)
    suspend fun deleteUser()
    suspend fun deletePersons()
    suspend fun insertPersons(persons: List<Person>)
    suspend fun getPersons() : List<Person>
    suspend fun findPersonById(id: String) : Person
    suspend fun createPerson(person: Person)
    suspend fun updatePerson(person: Person)
    suspend fun deletePerson(id: String)
    suspend fun deleteCases()
    suspend fun insertCases(cases: List<Case>)
    suspend fun getCases() : List<Case>
    suspend fun findCaseById(id: String) : Case
    suspend fun createCase(case: Case)
    suspend fun updateCase(case: Case)
    suspend fun deleteCase(id: String)

}