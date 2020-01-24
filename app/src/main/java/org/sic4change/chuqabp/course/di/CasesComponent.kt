package org.sic4change.chuqabp.course.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import org.sic4change.chuqabp.course.ui.login.LoginComponent
import org.sic4change.chuqabp.course.ui.login.LoginModule
import org.sic4change.chuqabp.course.ui.main.detail.DetailFragmentComponent
import org.sic4change.chuqabp.course.ui.main.detail.DetailFragmentModule
import org.sic4change.chuqabp.course.ui.main.main.MainFragmentComponent
import org.sic4change.chuqabp.course.ui.main.main.MainFragmentModule
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseFragmentComponent
import org.sic4change.chuqabp.course.ui.main.newcase.NewCaseFragmentModule
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseFragmentComponent
import org.sic4change.chuqabp.course.ui.main.updatecase.UpdateCaseFragmentModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface CasesComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent
    fun plus(module: NewCaseFragmentModule): NewCaseFragmentComponent
    fun plus(module: UpdateCaseFragmentModule): UpdateCaseFragmentComponent
    fun plus(module: LoginModule): LoginComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): CasesComponent
    }

}