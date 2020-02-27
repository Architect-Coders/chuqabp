package org.sic4change.chuqabp.course.ui.main.training

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.bttNavigation
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.FragmentTrainingBinding

class TrainingFragment: Fragment() {


    private var binding: FragmentTrainingBinding? = null

    private lateinit var navController: NavController


    private val viewModel : TrainingViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_training, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        navController = view.findNavController()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@TrainingFragment
        }

        bttNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.new_case -> {
                    navController.navigate(R.id.action_trainingFragment_to_newCase)
                    true
                }
                R.id.cases -> {
                    navController.navigate(R.id.action_trainingFragment_to_casesFragement)
                    true
                }
                R.id.persons -> {
                    navController.navigate(R.id.action_trainingFragment_to_mainFragment)
                    true
                }
                R.id.training -> {
                    true
                }
                R.id.configuration -> {
                    navController.navigate(R.id.action_trainingFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        bttNavigation.menu.findItem(R.id.training).isChecked = true
    }

}