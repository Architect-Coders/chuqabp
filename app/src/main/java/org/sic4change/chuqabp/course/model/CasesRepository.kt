package org.sic4change.chuqabp.course.model

import android.app.Activity
import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import timber.log.Timber

class CasesRepository(application: Application) {

    /**
     * Method to get cases
     */
    suspend fun getCases(): List<Case> {
        var cases : List<Case> = emptyList()
        return withContext(Dispatchers.IO) {
            try {
                val db = ChuqabpFirebaseService.mFirestore
                val casesRef = db.collection("cases")
                val query = casesRef.whereEqualTo("latitude", 0)
                val result = query.get().await()
                val networkUserContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
                cases = networkUserContainer.results
            } catch (ex: Exception) {
                Timber.d("Get user result: error ${ex.cause}")
            }
            return@withContext cases
        }
    }

}

