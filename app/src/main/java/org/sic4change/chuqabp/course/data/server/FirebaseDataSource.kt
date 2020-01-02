package org.sic4change.chuqabp.course.data.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.toDomainCase
import org.sic4change.data.source.RemoteDataSource


class FirebaseDataSource : RemoteDataSource {

    override suspend fun getCases(): List<org.sic4change.domain.Case> = withContext(Dispatchers.IO) {
            val firestore = ChuqabpFirebaseService.mFirestore
            val casesRef = firestore.collection("cases")
            val query = casesRef.whereEqualTo("latitude", 0)
            val result = query.get().await()
            val networkUserContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
            networkUserContainer.results.map { it.toDomainCase() }

    }

}