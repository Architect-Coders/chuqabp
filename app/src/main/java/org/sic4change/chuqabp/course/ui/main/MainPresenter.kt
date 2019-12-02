package org.sic4change.chuqabp.course.ui.main


import kotlinx.coroutines.launch
import org.sic4change.chuqabp.course.model.Case
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.common.Scope

class MainPresenter(private val casesRepository: CasesRepository) : Scope by Scope.Impl() {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(cases: List<Case>)
        fun navigateTo(case: Case)
    }

    private var view : View? = null

    fun onCreate(view: View) {
        initScope()
        this.view = view

        launch {
            view.showProgress()
            view.updateData(casesRepository.getCases())
            view.hideProgress()
        }

    }

    fun onDestroy() {
        cancelScope()
        this.view = null
    }

    fun onCaseClicked(case: Case) {
        view?.navigateTo(case)
    }

}