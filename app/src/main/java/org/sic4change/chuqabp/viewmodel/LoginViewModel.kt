package org.sic4change.chuqabp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.database.getDatabase
import org.sic4change.chuqabp.repository.UserRepository

/**
 * The [ViewModel] that is associated with the [LoginFragment].
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val loginRepository = UserRepository(getDatabase(application))

    /**
     * Login response from repository
     */
    val loginResponse = loginRepository.loginResponse

    /**
     * Request change password response from repository
     */
    val changePasswordResponse = loginRepository.changePasswordResponse

    /**
     * Create user response from repository
     */
    val createUserResponse = loginRepository.createUserResponse

    /**
     * User to show in views
     */
    val user = loginRepository.user

    /**
     * Method to login using repository
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password)
        }
    }

    /**
     * Method to forgot password using repository
     */
    fun forgotPassword(email: String) {
        viewModelScope.launch {
            loginRepository.forgotPassword(email)
        }
    }

    /**
     * Method to getUser using repository
     */
    fun getUser(email: String) {
        viewModelScope.launch {
            loginRepository.getUser(email)
        }
    }

    /**
     * Method to createUser using repository
     */
    fun createUser(email: String, password: String, name: String, surnames: String) {
        viewModelScope.launch {
            loginRepository.createUser(email, password, name, surnames)
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}