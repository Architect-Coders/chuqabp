package org.sic4change.usescases.users

import org.sic4change.data.repository.UserRepository

class Login(private val userRepository: UserRepository) {

    suspend fun invoke(email: String, password: String) : String = userRepository.login(email, password)

}