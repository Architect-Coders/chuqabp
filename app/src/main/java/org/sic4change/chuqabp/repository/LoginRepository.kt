package org.sic4change.chuqabp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.database.ChuqabpDatabase
import org.sic4change.chuqabp.database.asUserDomainModel
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.network.ChuqabpFirebaseService
import org.sic4change.chuqabp.network.NetworkUserContainer
import org.sic4change.chuqabp.network.User
import org.sic4change.chuqabp.network.asDatabaseModel
import timber.log.Timber

class LoginRepository(val database: ChuqabpDatabase) {

    var loginResponse : MutableLiveData<Models.LoginResponse> = MutableLiveData<Models.LoginResponse>(null)

    val user : LiveData<Models.User> = Transformations.map(database.chuqabpDatabaseDao.getUser()) {
        it?.asUserDomainModel()
    }

    /**
     * Method to login
     */
    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to login with firebase")
            try {
                val result = ChuqabpFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    Timber.d("Login result: ok")
                    loginResponse.postValue(Models.LoginResponse(true, user.email!!, ""))
                }
            } catch (ex : Exception) {
                Timber.d("Login result: false ${ex.cause}")
                loginResponse.postValue(Models.LoginResponse(false, "", ex.message.toString()))
            }
        }

    }

    /**
     * Method to get user
     */
    suspend fun getUser(email: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to get user from firebase")
            try {
                val db = ChuqabpFirebaseService.mFirestore
                val userRef = db.collection("users")
                val query = userRef.whereEqualTo("email", email).limit(1)
                val result = query.get().await()
                if (result != null) {
                    val networkUserContainer = NetworkUserContainer(result.toObjects(User::class.java))
                    Timber.d("Get user result:  ${networkUserContainer.resultados.get(0).email}")
                    database.chuqabpDatabaseDao.insertUser(networkUserContainer.resultados.get(0).asDatabaseModel())
                }
            } catch (ex: Exception) {
                Timber.d("Get user result: error ${ex.cause}")
            }

        }
    }

}