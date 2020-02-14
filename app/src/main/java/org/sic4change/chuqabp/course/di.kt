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
import org.sic4change.chuqabp.course.data.server.FirebaseDB
import org.sic4change.chuqabp.course.data.server.FirebaseDataSource
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.chuqabp.course.ui.login.create.CreateAccountFragment
import org.sic4change.chuqabp.course.ui.login.login.LoginFragment
import org.sic4change.chuqabp.course.ui.main.detail.DetailFragment
import org.sic4change.chuqabp.course.ui.main.detail.DetailViewModel
import org.sic4change.chuqabp.course.ui.main.main.MainFragment
import org.sic4change.chuqabp.course.ui.main.main.MainViewModel
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseFragment
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseViewModel
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseFragment
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseViewModel
import org.sic4change.chuqabp.course.ui.main.user.UserFragment
import org.sic4change.chuqabp.course.ui.main.user.UserViewModel
import org.sic4change.data.repository.CasesRepository
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
    //single(named("baseUrl")) {"https://firebasestorage.googleapis.com/v0/b/chuqabp.appspot.com/o/"}
    //single { FirebaseDB(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { CasesRepository(get(), get())}
    factory { UserRepository(get(), get())}
}

private val scopesModule = module {

    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get(), get(), get()) }
        scoped { GetCases(get()) }
        scoped { RefreshCases(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: String) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindCaseById(get()) }
        scoped { DeleteCase(get()) }
    }

    scope(named<NewCaseFragment>()) {
        viewModel { NewCaseViewModel(get(), get(), get()) }
        scoped { GetLocation(get()) }
        scoped { CreateCase(get()) }
    }

    scope(named<UpdateCaseFragment>()) {
        viewModel { (id: String) -> UpdateCaseViewModel(id, get(), get(), get()) }
        scoped { FindCaseById(get()) }
        scoped { UpdateCase(get()) }
    }

    scope(named<UserFragment>()) {
        viewModel { UserViewModel(get(), get()) }
        scoped { ChangePassword(get()) }
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