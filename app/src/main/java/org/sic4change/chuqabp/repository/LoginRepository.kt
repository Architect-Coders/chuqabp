package org.sic4change.chuqabp.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.network.ChuqabpFirebaseService
import timber.log.Timber

class LoginRepository {

    var loginResponse : MutableLiveData<Models.LoginResponse> = MutableLiveData<Models.LoginResponse>(null)

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to login with firebase")
            try {
                val result = ChuqabpFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    Timber.d("Login result: ok")
                    loginResponse.postValue(Models.LoginResponse(true, ""))
                }
            } catch (ex : Exception) {
                Timber.d("Login result: false ${ex.cause}")
                loginResponse.postValue(Models.LoginResponse(false, ex.message.toString()))
            }
        }

    }

}