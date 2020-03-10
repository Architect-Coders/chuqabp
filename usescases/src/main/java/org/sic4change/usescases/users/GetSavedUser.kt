package org.sic4change.usescases.users

import org.sic4change.data.repository.UserRepository
import org.sic4change.domain.User

class GetSavedUser (private val userRepository: UserRepository) {

    suspend fun invoke() : User? = userRepository.getUser()

}