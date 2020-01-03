package org.sic4change.usescases

import org.sic4change.data.repository.UserRepository

class ForgotPassword(private val userRepository: UserRepository) {

    suspend fun invoke(email: String) : Boolean = userRepository.forgotPassword(email)

}