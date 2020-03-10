package org.sic4change.usescases.users

import org.sic4change.data.repository.UserRepository

class GetUser (private val userRepository: UserRepository) {

    suspend fun invoke(email: String) {
        userRepository.getUser(email)
    }

}