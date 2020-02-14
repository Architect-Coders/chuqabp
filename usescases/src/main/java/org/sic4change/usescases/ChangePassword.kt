package org.sic4change.usescases

import org.sic4change.data.repository.UserRepository

class ChangePassword(private val userRepository: UserRepository) {

    suspend fun invoke() : Unit = userRepository.changePassword()

}