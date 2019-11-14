package org.sic4change.chuqabp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.network.ChuqabpFirebaseService
import timber.log.Timber

class LoginRepository {


    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to login with firebase")
            try {
                val result = ChuqabpFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    Timber.d("Login result: ok")
                } else {
                    Timber.d("Login result: false")
                }
            } catch (ex : Exception) {
                Timber.d("Login result: false ${ex.cause}")
            }
        }
    }

}