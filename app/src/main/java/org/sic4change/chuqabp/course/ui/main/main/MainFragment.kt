package org.sic4change.chuqabp.course.ui.main.main

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.PermissionRequester
import org.sic4change.chuqabp.course.ui.common.EventObserver
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var adapter : CasesAdapter

    private val coarsePermissionRequester by lazy {
        PermissionRequester(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private val viewModel : MainViewModel by currentScope.viewModel(this)

    private var binding: FragmentMainBinding? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_refresh).isVisible = true
        menu.findItem(R.id.action_add).isVisible = true
        menu.findItem(R.id.action_account).isVisible = true
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_edit).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                navController.navigate(R.id.action_mainFragment_to_newCaseFragment)
                true
            }
            R.id.action_refresh -> {
                viewModel.refreshCases()
                true
            }
            R.id.action_account -> {
                navController.navigate(R.id.action_mainFragment_to_userFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

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