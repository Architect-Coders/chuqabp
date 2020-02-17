package org.sic4change.chuqabp.course.data.server

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.toDomainCase
import org.sic4change.chuqabp.course.data.toDomainUser
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.User
import timber.log.Timber
import java.io.File
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
                val key: String = case.id
                val caseToCreate = Case(
                    key,
                    case.name,
                    case.surnames,
                    case.birthdate,
                    user?.id,
                    case.phone,
                    case.email,
                    case.photo,
                    case.location)
                casesRef.document(key).set(caseToCreate).await()
                Timber.d("Create case result: ok")
                if (case.photo.isNotEmpty()) {
                    uploadCaseFile(caseToCreate)
                }
            } catch (ex : Exception) {
                Timber.d("Create case result: false ${ex.message}")
            }
        }
    }

    private suspend fun uploadCaseFile(case: Case)  {
        withContext(Dispatchers.IO) {
            val storageRef = ChuqabpFirebaseService.mStorage.reference.child("cases/" + case.id)
            val file = Uri.fromFile(File(case.photo))
            storageRef.putFile(file).await()
        }

    }

    override suspend fun updateCase(user: User?, case: org.sic4change.domain.Case) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete case from firebase")
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val caseRef = firestore.collection("cases")
                val caseToUpdate = Case(
                    case.id,
                    case.name,
                    case.surnames,
                    case.birthdate,
                    user?.id,
                    case.phone,
                    case.email,
                    case.photo,
                    case.location)
                caseRef.document(case.id).set(caseToUpdate).await()
                Timber.d("Delete case result: ok")
                uploadCaseFile(caseToUpdate)
            } catch (ex: Exception) {
                Timber.d("Delete case result: false ${ex.message}")
            }
        }
    }

    override suspend fun deleteCase(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete case from firebase")
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val caseRef = firestore.collection("cases")
                caseRef.document(id).delete().await()
                Timber.d("Delete case result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete case result: false ${ex.message}")
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

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            ChuqabpFirebaseService.fbAuth.signOut()
        }
    }

    override suspend fun deleteUser(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete user from firebase")
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val userRef = firestore.collection("users")
                userRef.document(id).delete().await()
                Timber.d("Delete user result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete user result: false ${ex.message}")
            }
        }
    }

    override suspend fun forgotPassword(email: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            Timber.d("try to request change password with firebase")
            try {
                ChuqabpFirebaseService.fbAuth.sendPasswordResetEmail(email).await()
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

    override suspend fun changePassword(email: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to request change password with firebase")
            try {
                ChuqabpFirebaseService.fbAuth.sendPasswordResetEmail(email).await()
                Timber.d("Request change password: ok")
            } catch (ex : Exception) {
                Timber.d("Request change password: error ${ex.message}")
            }
        }
    }

}

