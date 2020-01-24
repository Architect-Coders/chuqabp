package org.sic4change.chuqabp.course.ui.main.detail

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.sic4change.data.repository.CasesRepository
import org.sic4change.usescases.DeleteCase
import org.sic4change.usescases.FindCaseById

@Module
class DetailFragmentModule(private val caseId: String) {

    @Provides
    fun detailViewModelProvider(findCaseById: FindCaseById, deleteCase: DeleteCase): DetailViewModel {
        return DetailViewModel(caseId, findCaseById, deleteCase)
    }

    @Provides
    fun findCaseByIdProvider(casesRepository: CasesRepository) = FindCaseById(casesRepository)

    @Provides
    fun deleteCaseProvider(casesRepository: CasesRepository) = DeleteCase(casesRepository)


}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModel: DetailViewModel
}