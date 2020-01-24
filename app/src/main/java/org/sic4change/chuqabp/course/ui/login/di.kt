package org.sic4change.chuqabp.course.ui.login

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.sic4change.data.repository.UserRepository
import org.sic4change.usescases.CreateUser
import org.sic4change.usescases.ForgotPassword
import org.sic4change.usescases.GetSavedUser
import org.sic4change.usescases.Login

@Module
class LoginModule{

    @Provides
    fun loginViewModelProvider(login: Login, forgotPassword: ForgotPassword, createUser: CreateUser, getSavedUser: GetSavedUser): LoginViewModel {
        return LoginViewModel(login, forgotPassword, createUser, getSavedUser)
    }

    @Provides
    fun loginProvider(userRepository: UserRepository) = Login(userRepository)

    @Provides
    fun forgotPasswordProvider(userRepository: UserRepository) = ForgotPassword(userRepository)

    @Provides
    fun createUserProvider(userRepository: UserRepository) = CreateUser(userRepository)

    @Provides
    fun getSavedUserProvider(userRepository: UserRepository) = GetSavedUser(userRepository)

}

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    val loginViewModel: LoginViewModel
}