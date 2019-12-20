package org.sic4change.chuqabp.course.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.UserRepository
import org.sic4change.chuqabp.course.ui.common.Scope

class LoginViewModel(private val userRepository: UserRepository) : ViewModel(),  Scope by Scope.Impl() {

    sealed class UIModel {
        object Loading: UIModel()
        object Enabled: UIModel()
        class ShowingLoginErrors(val message: String) : UIModel()
        class ShowingCreateAccountErrors(val message: String) : UIModel()
        class ShowingForgotPasswordResult(val result: Boolean) : UIModel()
        object NavigationToCreateAccount : UIModel()
        object NavigationToMain : UIModel()
    }

    private val _model = MutableLiveData<UIModel>()

    val model : LiveData<UIModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            val user = userRepository.getUser()
            if (user != null) _model.value = UIModel.NavigationToMain
        }
    }

    fun onLoginClicked(email: String, password: String) {
        launch {
            _model.value = UIModel.Loading
            val result = userRepository.login(email, password)
            val user = userRepository.getUser()
            if (user != null) _model.value = UIModel.NavigationToMain
            else _model.value = UIModel.ShowingLoginErrors(result)
        }
    }

    fun onCreateAccountClicked() {
        _model.value = UIModel.NavigationToCreateAccount
    }

    fun onForgotPasswordClicked(email: String) {
        launch {
            _model.value = UIModel.Loading
            if (userRepository.forgotPassword(email)) _model.value = UIModel.ShowingForgotPasswordResult(true)
            else _model.value = UIModel.ShowingForgotPasswordResult(false)
            _model.value = UIModel.Enabled
        }
    }

    fun onCreateNewUserClicked(email: String, password: String) {
        launch {
            _model.value = UIModel.Loading
            val result = userRepository.createUser(email, password)
            val user = userRepository.getUser()
            if (user != null) _model.value = UIModel.NavigationToMain
            else _model.value = UIModel.ShowingCreateAccountErrors(result)
        }
    }

    override fun onCleared() {
        destroyScope()
    }

}