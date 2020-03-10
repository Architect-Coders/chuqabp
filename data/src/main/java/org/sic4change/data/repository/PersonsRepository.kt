package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.Case
import org.sic4change.domain.Person

class PersonsRepository(private val localDataSource: LocalDataSource,
                        private val remoteDataSource: RemoteDataSource) {

    suspend fun refreshPersons() : List<Person> {
        val user = localDataSource.getUser()
        val persons = remoteDataSource.getPersons(user?.id)
        localDataSource.deletePersons()
        localDataSource.insertPersons(persons)
        return localDataSource.getPersons()
    }

    suspend fun getPersons() : List<Person> {
        /*if (localDataSource.getPersons().isEmpty()) {
            val user = localDataSource.getUser()
            val persons = remoteDataSource.getPersons(user?.id)
            localDataSource.deletePersons()
            localDataSource.insertPersons(persons)
        }
        return localDataSource.getPersons()*/
        val user = localDataSource.getUser()
        val persons = remoteDataSource.getPersons(user?.id)
        localDataSource.deletePersons()
        localDataSource.insertPersons(persons)
        return localDataSource.getPersons()
    }

    suspend fun getPersonsToSelect() : List<Person> {
        return localDataSource.getPersons()
    }

    suspend fun createPerson(person: Person) {
        val user = localDataSource.getUser()
        localDataSource.createPerson(person)
        remoteDataSource.createPerson(user, person)
    }

    suspend fun deletePerson(id: String) {
        localDataSource.deletePerson(id)
        remoteDataSource.deletePerson(id)
    }

    suspend fun updatePerson(person: Person) {
        val user = localDataSource.getUser()
        localDataSource.updatePerson(person)
        remoteDataSource.updatePerson(user, person)
    }

    suspend fun findPersonById(id: String): Person = localDataSource.findPersonById(id)

    suspend fun getCasesPerson(person: String) : List<Case> {
        return localDataSource.getPersonCases(person)
    }

    suspend fun filterPersons(nameSurnames: String, location: String) = localDataSource.filterPersons(nameSurnames, location)

}



