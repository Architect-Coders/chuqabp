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
 * The [ViewModel] that is associated with the [MainActivity].
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

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
    private val userRepository = UserRepository(getDatabase(application))


    /**
     * User to show in views
     */
    val user = userRepository.user

    /**
     * Method to getUser using repository
     */
    fun getUser() {
        viewModelScope.launch {
            userRepository.getUser()
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