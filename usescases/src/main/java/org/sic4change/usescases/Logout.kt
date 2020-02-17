package org.sic4change.usescases

import org.sic4change.data.repository.UserRepository

class Logout(private val userRepository: UserRepository) {

    suspend fun invoke() : Unit = userRepository.logout()

}