package org.sic4change.chuqabp.course.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.PermissionRequester
import org.sic4change.chuqabp.course.ui.common.app
import org.sic4change.chuqabp.course.ui.common.getViewModel
import org.sic4change.chuqabp.course.ui.detail.DetailActivity
import org.sic4change.chuqabp.course.ui.common.startActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    private lateinit var adapter : CasesAdapter

    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel{ MainViewModel(CasesRepository(app)) }

        adapter = CasesAdapter(viewModel::onCaseClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer {
            //::updateUI
            updateUI(viewModel.model.value!!)
        })
    }

    private fun updateUI(model: MainViewModel.UIModel) {
        progress.visibility = if (model == MainViewModel.UIModel.Loading) View.VISIBLE else View.GONE
        when(model) {
            is MainViewModel.UIModel.Content -> adapter.cases = model.cases
            is MainViewModel.UIModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.CASE, model.case.id)
            }
            MainViewModel.UIModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        }
    }


}
