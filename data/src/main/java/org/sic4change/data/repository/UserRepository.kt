package org.sic4change.data.repository

import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.User

class UserRepository (private val localDataSource: LocalDataSource,
                      private val remoteDataSource: RemoteDataSource) {

    suspend fun getUser(email: String) {
        val user = remoteDataSource.getUser(email)
        localDataSource.deleteUser()
        localDataSource.insertUser(user)
    }

    suspend fun getUser() : User? {
        return localDataSource.getUser()
    }

    suspend fun login(email: String, password: String) : String {
        val result = remoteDataSource.login(email, password)
       if (result == "logged") {
           getUser(email)
       }
        return result
    }

    suspend fun forgotPassword(email: String) : Boolean = remoteDataSource.forgotPassword(email)

    suspend fun createUser(email: String, password: String) : String {
        val result = remoteDataSource.createUser(email, password)
        if (result == "created") {
            getUser(email)
        }
        return result
    }

}
