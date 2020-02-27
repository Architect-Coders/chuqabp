package org.sic4change.chuqabp.course.ui.main.main

import android.Manifest
import android.os.Bundle
import android.os.CountDownTimer
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


@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    private lateinit var adapter : PersonsAdapter

    private val coarsePermissionRequester by lazy {
        PermissionRequester(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private val viewModel : MainViewModel by currentScope.viewModel(this)

    private var binding: FragmentMainBinding? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        swipe_container.setColorSchemeColors(resources.getColor(R.color.colorPrimaryDark),
            resources.getColor(R.color.colorPrimaryDark), resources.getColor(R.color.colorPrimaryDark))

        viewModel.navigateToPerson.observe(this, EventObserver{ id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequest()
            }
        })

        adapter = PersonsAdapter(viewModel::onPersonClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }

        bttNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.new_case -> {
                    navController.navigate(R.id.action_mainFragment_to_newCase)
                    true
                }
                R.id.cases -> {
                    navController.navigate(R.id.action_mainFragment_to_casesFragement)
                    true
                }
                R.id.persons -> {
                    true
                }
                R.id.training -> {
                    navController.navigate(R.id.action_mainFragment_to_trainingFragment)
                    true
                }
                R.id.configuration -> {
                    navController.navigate(R.id.action_mainFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

        btnNewPerson.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_newPersonFragment)
        }

        swipe_container.setOnRefreshListener {
            viewModel.refreshPersons()
            val timer = object: CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    if (swipe_container != null && swipe_container.isRefreshing) {
                        swipe_container.isRefreshing = false
                    }
                }
            }
            timer.start()
        }

    }

    override fun onResume() {
        super.onResume()
        bttNavigation.menu.findItem(R.id.persons).isChecked = true
    }

}