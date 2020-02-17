package org.sic4change.chuqabp.course.data.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.*
import org.sic4change.data.source.LocalDataSource
import org.sic4change.domain.User as DomainUser
import org.sic4change.domain.Person as DomainPerson

class RoomDataSource(db : ChuqabpDatabase) : LocalDataSource {

    private val chuqabpDao = db.chuqabpDao()

    override suspend fun getUser(): DomainUser? = withContext(Dispatchers.IO) {
        val user = chuqabpDao.getUser()
        if (user != null) {
            user.toDomainUser()
        } else {
            null
        }
    }

    override suspend fun getUser(id: String): DomainUser? = withContext(Dispatchers.IO) {
        chuqabpDao.getUser(id).toDomainUser()
    }

    override suspend fun createPerson(person: org.sic4change.domain.Person) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertPerson(person.toDatabasePerson())
        }
    }

    override suspend fun updatePerson(case: org.sic4change.domain.Person) {
        withContext(Dispatchers.IO) {
            chuqabpDao.updatePerson(case.id, case.name, case.surnames, case.birthdate, case.phone, case.email, case.photo, case.location)
        }
    }

    override suspend fun deletePerson(id: String) {
        withContext(Dispatchers.IO) {
            chuqabpDao.deletePerson(id)
        }
    }

    override suspend fun insertUser(user: DomainUser) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertUser(user.toDatabaseUser())
        }
    }

    override suspend fun deleteUser() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteUser()
        }
    }

    override suspend fun deletePersons() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deletePersons()
        }
    }

    override suspend fun insertPersons(persons: List<DomainPerson>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertPersons(persons.map { it.toDatabasePerson() })
        }
    }

    override suspend fun getPersons(): List<DomainPerson> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllPersons().map { it.toDomainPerson() }
    }

    override suspend fun findById(id: String): DomainPerson = withContext(Dispatchers.IO) {
        chuqabpDao.findPersonById(id).toDomainPerson()
    }

}





