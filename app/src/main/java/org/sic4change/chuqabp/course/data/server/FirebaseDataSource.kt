package org.sic4change.chuqabp.course.data.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.toDomainCase
import org.sic4change.chuqabp.course.data.toDomainUser
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.User
import timber.log.Timber
import org.sic4change.chuqabp.course.data.server.User as NewtworkUser


class FirebaseDataSource : RemoteDataSource {

    override suspend fun getCases(mentorId: String?): List<org.sic4change.domain.Case> = withContext(Dispatchers.IO) {
            val firestore = ChuqabpFirebaseService.mFirestore
            val casesRef = firestore.collection("cases")
            val query = casesRef.whereEqualTo("mentorId", mentorId)
            val result = query.get().await()
            val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
            networkCasesContainer.results.map { it.toDomainCase() }
    }

    override suspend fun createCase(user: User?, case: org.sic4change.domain.Case) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create case with firebase")
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val casesRef = firestore.collection("cases")
                val newCase = casesRef.add(case).await()
                casesRef.document(newCase.id).set(
                    Case(newCase.id,
                        case.name,
                        case.surnames,
                        case.birthdate,
                        user?.id,
                        case.phone,
                        case.email,
                        case.photo,
                        case.location)).await()
                Timber.d("Create case result: ok")
            } catch (ex : Exception) {
                Timber.d("Create case result: false ${ex.message}")
            }
        }
    }

    override suspend fun getUser(email: String): User = withContext(Dispatchers.IO) {
        val firestore = ChuqabpFirebaseService.mFirestore
        val userRef = firestore.collection("users")
        val query = userRef.whereEqualTo("email", email).limit(1)
        val result = query.get().await()
        val networkUserContainer = NetworkUserContainer(result.toObjects(NewtworkUser::class.java))
        networkUserContainer.results[0].let {
            it.toDomainUser()
        }
    }

    override suspend fun login(email: String, password: String): String {
        var result = "logged"
        withContext(Dispatchers.IO) {
            try {
                val login = ChuqabpFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = login.user
                if (user != null) {
                    Timber.d("Login result: ok")
                }
            } catch (ex: Exception) {
                Timber.d("Login result: error ${ex.message}")
                result = ex.message.toString()
            }
        }
        return result
    }

    override suspend fun forgotPassword(email: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            Timber.d("try to request change password with firebase")
            try {
                val forgotPassword = ChuqabpFirebaseService.fbAuth.sendPasswordResetEmail(email).await()
                Timber.d("Request change password: ok")
                result = true
            } catch (ex : Exception) {
                Timber.d("Request change password: error ${ex.message}")
            }
        }
        return result
    }

    override suspend fun createUser(email: String, password: String): String {
        var result = "created"
        withContext(Dispatchers.IO) {
            Timber.d("try to create user with firebase")
            try {
                ChuqabpFirebaseService.fbAuth.createUserWithEmailAndPassword(email, password).await()
                val firestore = ChuqabpFirebaseService.mFirestore
                val userRef = firestore.collection("users")
                val newUser = userRef.add(User("", email, "", "", "")).await()
                userRef.document(newUser.id).set(User(newUser.id, email, "", "", "")).await()
                Timber.d("CreateUser result: ok")
            } catch (ex : Exception) {
                Timber.d("Create user result: false ${ex.message}")
                result = ex.message.toString()
            }
        }
        return result
    }
}

