package org.sic4change.chuqabp.course.ui.detail

import org.sic4change.chuqabp.course.model.Case


class DetailPresenter {

    private var view: View? = null

    interface View {
        fun updateUI(case: Case)
    }

    fun onCreate(view: View, case: Case) {
        this.view = view
        view.updateUI(case)
    }

    fun onDestroy() {
        view = null
    }
}