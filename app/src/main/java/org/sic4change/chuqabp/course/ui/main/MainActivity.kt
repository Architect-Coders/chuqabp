package org.sic4change.chuqabp.course.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.PermissionRequester
import org.sic4change.chuqabp.course.ui.common.EventObserver
import org.sic4change.chuqabp.course.ui.common.app
import org.sic4change.chuqabp.course.ui.common.getViewModel
import org.sic4change.chuqabp.course.ui.detail.DetailActivity
import org.sic4change.chuqabp.course.ui.common.startActivity
import org.sic4change.chuqabp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    private lateinit var adapter : CasesAdapter

    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel{ MainViewModel(CasesRepository(app)) }

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = CasesAdapter(viewModel::onCaseClicked)
        binding.recycler.adapter = adapter

        viewModel.navigateToCase.observe(this, EventObserver{ id ->
            startActivity<DetailActivity> {
                putExtra(DetailActivity.CASE, id)
            }
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        })
    }

}
