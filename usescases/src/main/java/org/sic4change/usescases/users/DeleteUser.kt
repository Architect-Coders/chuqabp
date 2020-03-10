package org.sic4change.usescases.users

import org.sic4change.data.repository.UserRepository

class DeleteUser(private val userRepository: UserRepository) {

    suspend fun invoke() : Unit = userRepository.deleteUser()

}