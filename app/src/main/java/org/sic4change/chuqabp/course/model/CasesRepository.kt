package org.sic4change.chuqabp.course.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.ChuqabpApp
import org.sic4change.chuqabp.course.model.database.Case
import org.sic4change.chuqabp.course.model.server.ChuqabpFirebaseService
import org.sic4change.chuqabp.course.model.server.NetworkCasesContainer

import timber.log.Timber

class CasesRepository(application: ChuqabpApp) {

    private val db = application.db

    /*suspend fun findCases(): List<Case> = withContext(Dispatchers.IO){
        with(db.chuqabpDao()) {
            if (caseCount() <= 0) {
                try {
                    val firestore = ChuqabpFirebaseService.mFirestore
                    val casesRef = firestore.collection("cases")
                    val query = casesRef.whereEqualTo("latitude", 0)
                    val result = query.get().await()
                    val networkUserContainer =
                        NetworkCasesContainer(
                            result.toObjects(Case::class.java)
                        )
                    insertCases(networkUserContainer.results)
                } catch (ex: Exception) {
                    Timber.d("Get user result: error ${ex.cause}")
                }
            }
            getAllCases()
        }
    }*/

    suspend fun getCases(): List<Case> = withContext(Dispatchers.IO) {
            with(db.chuqabpDao()) {
                getAllCases()
            }
    }

    suspend fun refresh() {
        withContext(Dispatchers.IO){
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val casesRef = firestore.collection("cases")
                val query = casesRef.whereEqualTo("latitude", 0)
                val result = query.get().await()
                val networkUserContainer =
                    NetworkCasesContainer(
                        result.toObjects(Case::class.java)
                    )
                with(db.chuqabpDao()) {
                    deleteCases()
                    insertCases(networkUserContainer.results)
                }
            } catch (ex: Exception) {
                Timber.d("Get user result: error ${ex.cause}")
            }
        }
    }


    suspend fun findCaseById(id: String): Case = withContext(Dispatchers.IO) {
        db.chuqabpDao().findCaseById(id)
    }


}

