package org.sic4change.data.source

import org.sic4change.domain.Person
import org.sic4change.domain.User

interface LocalDataSource {
    suspend fun getUser() : User?
    suspend fun createPerson(person: Person)
    suspend fun updatePerson(person: Person)
    suspend fun deletePerson(id: String)
    suspend fun getUser(id: String) : User?
    suspend fun insertUser(user: User)
    suspend fun deleteUser()
    suspend fun deletePersons()
    suspend fun insertPersons(persons: List<Person>)
    suspend fun getPersons() : List<Person>
    suspend fun findById(id: String) : Person

}