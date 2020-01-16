package org.sic4change.chuqabp.course.data.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.toDatabaseCase
import org.sic4change.chuqabp.course.data.toDatabaseUser
import org.sic4change.chuqabp.course.data.toDomainCase
import org.sic4change.chuqabp.course.data.toDomainUser
import org.sic4change.data.source.LocalDataSource
import org.sic4change.domain.User as DomainUser
import org.sic4change.domain.Case as DomainCase

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

    override suspend fun createCase(case: org.sic4change.domain.Case) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertCase(case.toDatabaseCase())
        }
    }

    override suspend fun deleteCase(id: String) {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteCase(id)
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

    override suspend fun deleteCases() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteCases()
        }
    }

    override suspend fun insertCases(cases: List<DomainCase>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertCases(cases.map { it.toDatabaseCase() })
        }
    }

    override suspend fun getCases(): List<DomainCase> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllCases().map { it.toDomainCase() }
    }

    override suspend fun findById(id: String): DomainCase = withContext(Dispatchers.IO) {
        chuqabpDao.findCaseById(id).toDomainCase()
    }

}





