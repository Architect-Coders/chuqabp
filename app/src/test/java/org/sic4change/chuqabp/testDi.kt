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
import org.sic4change.domain.Case
import org.sic4change.domain.User
import org.sic4change.testshared.mockedCase
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

val defaultFakeCases = mutableListOf(
    mockedCase.copy("AAAAA"),
    mockedCase.copy("BBBBB"),
    mockedCase.copy("CCCCC"),
    mockedCase.copy("DDDDD")
)

val defaultFakeUser = mockedUser.copy("XXXXX")


class FakeLocalDataSource : LocalDataSource {

    var cases: MutableList<Case> = arrayListOf()

    var user: User? = defaultFakeUser


    override suspend fun insertCases(cases: List<Case>) {
        this.cases.clear()
        this.cases.addAll(cases)
    }

    override suspend fun getCases(): List<Case> = cases

    override suspend fun findById(id: String): Case = cases.first { it.id == id }

    override suspend fun getUser(): User? = user

    override suspend fun getUser(id: String): User? = user

    override suspend fun createCase(case: Case) {
        //cases.add(case)
    }

    override suspend fun updateCase(case: Case) {
        cases[0] = case
    }

    override suspend fun deleteCase(id: String) {
        //cases.removeAt(3)
    }

    override suspend fun insertUser(user: User) {
        //this.user = user
    }

    override suspend fun deleteUser() {
        //this.user = null
    }

    override suspend fun deleteCases() {
        cases.clear()
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var cases: MutableList<Case> = defaultFakeCases
    var user = defaultFakeUser

    override suspend fun getCases(mentorId: String?): List<Case> = cases

    override suspend fun createCase(user: User?, case: Case) {
        cases.add(case)
    }

    override suspend fun updateCase(user: User?, case: Case) {
        cases[0] = case
    }

    override suspend fun deleteCase(id: String) {
        cases.removeAt(3)
    }

    override suspend fun getUser(email: String): User {
        return user
    }

    override suspend fun login(email: String, password: String): String {
        return "login"
    }

    override suspend fun forgotPassword(email: String): Boolean {
        return true
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