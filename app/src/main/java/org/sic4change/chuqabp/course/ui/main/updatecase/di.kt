package org.sic4change.chuqabp.course.ui.main.updatecase

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.sic4change.data.repository.CasesRepository
import org.sic4change.usescases.FindCaseById
import org.sic4change.usescases.UpdateCase

@Module
class UpdateCaseFragmentModule(private val caseId: String) {

    @Provides
    fun updateCaseViewModelProvider(findCaseById: FindCaseById, updateCase: UpdateCase): UpdateCaseViewModel {
        return UpdateCaseViewModel(caseId, findCaseById, updateCase)
    }

    @Provides
    fun findCaseByIdProvider(casesRepository: CasesRepository) = FindCaseById(casesRepository)

    @Provides
    fun updateCaseProvider(casesRepository: CasesRepository) = UpdateCase(casesRepository)


}

@Subcomponent(modules = [UpdateCaseFragmentModule::class])
interface UpdateCaseFragmentComponent {
    val updateCaseViewModel: UpdateCaseViewModel
}