package org.sic4change.chuqabp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.UserRepository


class MainViewModel(application: Application) : AndroidViewModel(application) {


    /*private val viewModelJob = SupervisorJob()


    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val userRepository =
        UserRepository(
            getDatabase(application)
        )



    val user = userRepository.user


    fun getUser() {
        viewModelScope.launch {
            userRepository.getUser()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/

}