package org.sic4change.chuqabp.course

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.sic4change.chuqabp.course.data.AndroidPermissionChecker
import org.sic4change.chuqabp.course.data.PlayServicesLocationDataSource
import org.sic4change.chuqabp.course.data.database.ChuqabpDatabase
import org.sic4change.chuqabp.course.data.database.RoomDataSource
import org.sic4change.chuqabp.course.data.server.FirebaseDataSource
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.chuqabp.course.ui.login.create.CreateAccountFragment
import org.sic4change.chuqabp.course.ui.login.login.LoginFragment
import org.sic4change.chuqabp.course.ui.main.detail.DetailFragment
import org.sic4change.chuqabp.course.ui.main.detail.DetailViewModel
import org.sic4change.chuqabp.course.ui.main.main.MainFragment
import org.sic4change.chuqabp.course.ui.main.main.MainViewModel
import org.sic4change.chuqabp.course.ui.main.newperson.NewPersonFragment
import org.sic4change.chuqabp.course.ui.main.newperson.NewPersonViewModel
import org.sic4change.chuqabp.course.ui.main.updateperson.UpdatePersonFragment
import org.sic4change.chuqabp.course.ui.main.updateperson.UpdatePersonViewModel
import org.sic4change.chuqabp.course.ui.main.user.UserFragment
import org.sic4change.chuqabp.course.ui.main.user.UserViewModel
import org.sic4change.data.repository.PersonsRepository
import org.sic4change.data.repository.PermissionChecker
import org.sic4change.data.repository.RegionRepository
import org.sic4change.data.repository.UserRepository
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.LocationDataSource
import org.sic4change.data.source.RemoteDataSource
import org.sic4change.usescases.*

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { ChuqabpDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource>{ FirebaseDataSource() }
    factory<LocationDataSource>{ PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get())}
    single<CoroutineDispatcher> { Dispatchers.Main}
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { PersonsRepository(get(), get())}
    factory { UserRepository(get(), get())}
}

private val scopesModule = module {

    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get(), get(), get()) }
        scoped { GetPersons(get()) }
        scoped { RefreshPersons(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: String) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindPersonById(get()) }
        scoped { DeletePerson(get()) }
    }

    scope(named<NewPersonFragment>()) {
        viewModel { NewPersonViewModel(get(), get(), get()) }
        scoped { GetLocation(get()) }
        scoped { CreatePerson(get()) }
    }

    scope(named<UpdatePersonFragment>()) {
        viewModel { (id: String) -> UpdatePersonViewModel(id, get(), get(), get()) }
        scoped { FindPersonById(get()) }
        scoped { UpdatePerson(get()) }
    }

    scope(named<UserFragment>()) {
        viewModel { UserViewModel(get(), get(), get(), get()) }
        scoped { ChangePassword(get()) }
        scoped { Logout(get()) }
        scoped { DeleteUser(get()) }
    }

    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
        scoped { Login(get()) }
        scoped { ForgotPassword(get()) }
        scoped { CreateUser(get()) }
        scoped { GetSavedUser(get()) }
    }

    scope(named<CreateAccountFragment>()) {
        viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
        scoped { Login(get()) }
        scoped { ForgotPassword(get()) }
        scoped { CreateUser(get()) }
        scoped { GetSavedUser(get()) }
    }

}