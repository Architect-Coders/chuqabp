package org.sic4change.chuqabp.course.data.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.toDatabaseCase
import org.sic4change.chuqabp.course.data.toDomainCase
import org.sic4change.data.source.LocalDataSource
import org.sic4change.domain.Case as DomainCase

class RoomDataSource(db : ChuqabpDatabase) : LocalDataSource {

    private val chuqabpDao = db.chuqabpDao()

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

    override suspend fun getCases(): List<DomainCase> =
        withContext(Dispatchers.IO) {
            chuqabpDao.getAllCases().map { it.toDomainCase() }
        }

    override suspend fun findById(id: String): DomainCase = withContext(Dispatchers.IO) {
            chuqabpDao.findCaseById(id).toDomainCase()
    }

}





