package org.sic4change.chuqabp.course.ui.main.main

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.sic4change.data.repository.CasesRepository
import org.sic4change.usescases.GetCases

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(getCases: GetCases) = MainViewModel(getCases)

    @Provides
    fun getCasesProvider(casesRepository: CasesRepository) =
        GetCases(casesRepository)
}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModel: MainViewModel
}