package org.sic4change.chuqabp.course.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.common.CoroutineScopeActivity


class MainActivity : CoroutineScopeActivity() {

    private val casesRepository: CasesRepository by lazy {
        CasesRepository()
    }

    private val adapter = CasesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.CASE, it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        launch {
            adapter.cases = casesRepository.getCases()
        }
    }


}
