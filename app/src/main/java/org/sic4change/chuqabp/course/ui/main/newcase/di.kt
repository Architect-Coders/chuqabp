package org.sic4change.chuqabp.course.ui.main.newcase

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.sic4change.data.repository.CasesRepository
import org.sic4change.data.repository.RegionRepository
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetLocation

@Module
class NewCaseFragmentModule {

    @Provides
    fun newCaseViewModelProvider(getLocation: GetLocation, createCase: CreateCase) = NewCaseViewModel(getLocation, createCase)

    @Provides
    fun getLocationProvider(regionRepository: RegionRepository) =
        GetLocation(regionRepository)

    @Provides
    fun createCaseProvider(casesRepository: CasesRepository) =
        CreateCase(casesRepository)
}

@Subcomponent(modules = [NewCaseFragmentModule::class])
interface NewCaseFragmentComponent {
    val newCaseViewModel: NewCaseViewModel
}