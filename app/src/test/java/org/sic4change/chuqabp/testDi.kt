package org.sic4change.chuqabp

import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.sic4change.chuqabp.course.dataModule
import org.sic4change.data.repository.PermissionChecker
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.LocationDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.domain.Person
import org.sic4change.domain.User
import org.sic4change.testshared.mockedPerson
import org.sic4change.testshared.mockedUser

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule: Module = module {
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakePersons = mutableListOf(
    mockedPerson.copy("AAAAA"),
    mockedPerson.copy("BBBBB"),
    mockedPerson.copy("CCCCC"),
    mockedPerson.copy("DDDDD")
)

val defaultFakeUser = mockedUser.copy("XXXXX")


class FakeLocalDataSource : LocalDataSource {

    var persons: MutableList<Person> = arrayListOf()

    var user: User? = defaultFakeUser


    override suspend fun insertPersons(persons: List<Person>) {
        this.persons.clear()
        this.persons.addAll(persons)
    }

    override suspend fun getPersons(): List<Person> = persons

    override suspend fun findPersonById(id: String): Person = persons.first { it.id == id }

    override suspend fun getUser(): User? = user

    override suspend fun getUser(id: String): User? = user

    override suspend fun createPerson(person: Person) {
        //persons.add(person)
    }

    override suspend fun updatePerson(person: Person) {
        persons[0] = person
    }

    override suspend fun deletePerson(id: String) {
        //persons.removeAt(3)
    }

    override suspend fun insertUser(user: User) {
        //this.user = user
    }

    override suspend fun deleteUser() {
        //this.user = null
    }

    override suspend fun deletePersons() {
        persons.clear()
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var persons: MutableList<Person> = defaultFakePersons
    var user = defaultFakeUser

    override suspend fun getPersons(mentorId: String?): List<Person> = persons

    override suspend fun createPerson(user: User?, person: Person) {
        persons.add(person)
    }

    override suspend fun updatePerson(user: User?, person: Person) {
        persons[0] = person
    }

    override suspend fun deletePerson(id: String) {
        persons.removeAt(3)
    }

    override suspend fun getUser(email: String): User {
        return user
    }

    override suspend fun login(email: String, password: String): String {
        return "login"
    }

    override suspend fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteUser(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun forgotPassword(email: String): Boolean {
        return true
    }

    override suspend fun changePassword(email: String) {
        return
    }

    override suspend fun createUser(email: String, password: String): String {
        return "created"
    }
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
    override suspend fun findLastLatitude(): Double? {
        return 0.0
    }

    override suspend fun findLastLongitude(): Double? {
        return 0.0
    }
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}