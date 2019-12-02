package org.sic4change.chuqabp.course.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.Case
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.detail.DetailActivity
import org.sic4change.chuqabp.course.ui.common.startActivity


class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy {  MainPresenter(CasesRepository(this)) }


    private val adapter = CasesAdapter {
        presenter.onCaseClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(this)

        recycler.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun updateData(cases: List<Case>) {
        adapter.cases = cases
    }

    override fun navigateTo(case: Case) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.CASE, case)
        }
    }




}
