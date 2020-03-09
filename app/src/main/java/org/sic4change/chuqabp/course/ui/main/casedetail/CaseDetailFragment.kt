package org.sic4change.chuqabp.course.ui.main.casedetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_case_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.main.newcase.ResourcesAdapter
import org.sic4change.chuqabp.databinding.FragmentCaseDetailBinding

class CaseDetailFragment : Fragment() {

    private lateinit var adapter : ResourcesAdapter

    private var binding: FragmentCaseDetailBinding? = null

    private lateinit var navController: NavController

    private val args: CaseDetailFragmentArgs by navArgs()

    private val viewModel : CaseDetailViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_case_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()

        adapter = ResourcesAdapter(viewModel::onResourceClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@CaseDetailFragment
        }

        viewModel.deleted.observe(this, Observer<Boolean> {
            if (it) {
                val action = CaseDetailFragmentDirections.actionCaseDetailFragmentToCasesFragment()
                navController.navigate(action)
            }
        })

        btnEditCase.setOnClickListener {
            val action = CaseDetailFragmentDirections.actionCaseDetailFragmentToUpdateCaseFragment(args.id)
            navController.navigate(action)
        }

        btnDeleteCase.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        btnCancelCase.setOnClickListener {
            val action = CaseDetailFragmentDirections.actionCaseDetailFragmentToCasesFragment()
            navController.navigate(action)
        }

        btnCloseCase.setOnClickListener {
            val action = CaseDetailFragmentDirections.actionCaseDetailFragmentToCloseCaseFragment(args.id)
            navController.navigate(action)
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)
        builder.setTitle(R.string.delete_case_question_title)
        builder.setMessage(R.string.delete_Case_question_description)
        builder.setCancelable(true)
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteCase()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun finish() {
        navController.navigate(R.id.action_caseDetailFragment_to_cases)
        activity?.finish()
    }


}