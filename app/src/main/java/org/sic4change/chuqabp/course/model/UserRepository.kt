package org.sic4change.chuqabp.course.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.ChuqabpApp
import org.sic4change.chuqabp.course.model.database.DatabaseUser
import org.sic4change.chuqabp.course.model.server.ChuqabpFirebaseService
import org.sic4change.chuqabp.course.model.server.NetworkUserContainer
import org.sic4change.chuqabp.course.model.server.User
import org.sic4change.chuqabp.course.model.server.asDatabaseModel
import timber.log.Timber

class UserRepository(application: ChuqabpApp) {

    private val db = application.db

    suspend fun getUser(email: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to get user from firebase")
            try {
                val firestore = ChuqabpFirebaseService.mFirestore
                val userRef = firestore.collection("users")
                val query = userRef.whereEqualTo("email", email).limit(1)
                val result = query.get().await()
                if (result != null ) {
                    val networkUserContainer =
                        NetworkUserContainer(
                            result.toObjects(User::class.java)
                        )
                    Timber.d("Get user result:  ${networkUserContainer.resultados[0].email}")
                    db.chuqabpDao().deleteUser()
                    db.chuqabpDao().insertUser(networkUserContainer.resultados[0].asDatabaseModel())
                }
            } catch (ex: Exception) {
                Timber.d("Get user result: error ${ex.cause}")
            }
        }
    }

    suspend fun getUser(): DatabaseUser = withContext(Dispatchers.IO) {
        db.chuqabpDao().getUser()
    }

    suspend fun login(email: String, password: String) : String {
        var result = "logged"
        withContext(Dispatchers.IO) {
            Timber.d("try to login with firebase")
            try {
                val result = ChuqabpFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    Timber.d("Login result: ok")
                    user.email?.let { getUser(it) }
                }
            } catch (ex : Exception) {
                Timber.d("Login result: false ${ex.message}")
                result = ex.message.toString()
            }
        }
        return result
    }

    suspend fun forgotPassword(email: String) : Boolean {
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

    suspend fun createUser(email: String, password: String) : String {
        var result = "created"
        withContext(Dispatchers.IO) {
            Timber.d("try to create user with firebase")
            try {
                ChuqabpFirebaseService.fbAuth.createUserWithEmailAndPassword(email, password).await()
                val firestore = ChuqabpFirebaseService.mFirestore
                val userRef = firestore.collection("users")
                val newUser = userRef.add(User("", email, "", "", "")).await()
                userRef.document(newUser.id).set(User(newUser.id, email, "", "", "")).await()
                getUser(email)
                Timber.d("CreateUser result: ok")
            } catch (ex : Exception) {
                Timber.d("Create user result: false ${ex.message}")
                result = ex.message.toString()
            }
        }
        return result
    }

}