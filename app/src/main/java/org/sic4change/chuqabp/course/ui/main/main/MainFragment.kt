package org.sic4change.chuqabp.course.ui.main.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.PermissionRequester
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.course.ui.main.MainViewModel
import org.sic4change.chuqabp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    private lateinit var adapter : CasesAdapter

    private val coarsePermissionRequester by lazy {
        PermissionRequester(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private var binding: FragmentMainBinding? = null

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        viewModel = getViewModel {
            MainViewModel(CasesRepository(app))
        }

        viewModel.navigateToCase.observe(this, EventObserver{ id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        })

        adapter = CasesAdapter(viewModel::onCaseClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }

}