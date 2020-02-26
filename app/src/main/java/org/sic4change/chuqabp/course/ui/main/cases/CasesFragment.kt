package org.sic4change.chuqabp.course.ui.main.cases

import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_cases.*
import kotlinx.android.synthetic.main.fragment_main.bttNavigation
import kotlinx.android.synthetic.main.fragment_main.recycler
import kotlinx.android.synthetic.main.fragment_main.swipe_container
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.EventObserver
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.FragmentCasesBinding

class CasesFragment: Fragment() {

    private lateinit var adapter : CasesAdapter

    private var binding: FragmentCasesBinding? = null

    private lateinit var navController: NavController


    private val viewModel : CasesViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_cases, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        navController = view.findNavController()

        swipe_container.setColorSchemeColors(resources.getColor(R.color.colorPrimaryDark),
            resources.getColor(R.color.colorPrimaryDark), resources.getColor(R.color.colorPrimaryDark))

        viewModel.navigateToCase.observe(this, EventObserver{ id ->
            val action = CasesFragmentDirections.actionCasesFragmentToCaseDetailFragment(id)
            navController.navigate(action)
        })

        adapter = CasesAdapter(viewModel::onCaseClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@CasesFragment
        }

        bttNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.new_case -> {
                    navController.navigate(R.id.action_casesFragment_to_newCaseFragement)
                    true
                }
                R.id.cases -> {
                    true
                }
                R.id.persons -> {
                    navController.navigate(R.id.action_casesFragment_to_mainFragment)
                    true
                }
                R.id.training -> {
                    navController.navigate(R.id.action_casesFragment_to_trainingFragment)
                    true
                }
                R.id.configuration -> {
                    navController.navigate(R.id.action_casesFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

        btnNewCase.setOnClickListener {
            navController.navigate(R.id.action_casesFragment_to_newCaseFragement)
        }

        swipe_container.setOnRefreshListener {
            viewModel.refreshCases()
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_refresh).isVisible = false
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_account).isVisible = false
        menu.findItem(R.id.action_edit).isVisible = false
        menu.findItem(R.id.action_delete).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        bttNavigation.menu.findItem(R.id.cases).isChecked = true
    }

}