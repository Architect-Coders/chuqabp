package org.sic4change.usescases.users

import org.sic4change.data.repository.UserRepository

class CreateUser(private val userRepository: UserRepository) {

    suspend fun invoke(email: String, password: String) : String = userRepository.createUser(email, password)

}