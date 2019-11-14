package org.sic4change.chuqabp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.repository.LoginRepository
import java.io.IOException

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
    private val loginRepository = LoginRepository()

    /**
     * An application user
     */
    //val videos = videosRepository.videos

    /**
     * Flag to naviate to main view. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _navigateToMainView = MutableLiveData<Boolean>()

    /**
     * Flag to naviate to main view. Views should use this to get access
     * to the data.
     */
    val navigateToMainView: LiveData<Boolean>
        get() = _navigateToMainView

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError



    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                loginRepository.login(email, password)
            } catch (networkError: IOException) {
                _eventNetworkError.value = true
            }
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