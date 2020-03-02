package org.sic4change.chuqabp.course.ui.main.detail

import android.app.AlertDialog
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.recycler
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.EventObserver
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {

    private lateinit var adapter : CasesPersonAdapter

    private var binding: FragmentDetailBinding? = null

    private lateinit var navController: NavController

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel : DetailViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()

        adapter = CasesPersonAdapter(viewModel::onCaseClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }

        viewModel.deleted.observe(this, Observer<Boolean> {
            if (it) {
                finish()
            }
        })

        btnEditPerson.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToUpdatePersonFragment(args.id)
            navController.navigate(action)
        }

        btnDeletePerson.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        btnCancelCase.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToPersonsFragment()
            navController.navigate(action)
        }

        viewModel.navigateToCase.observe(this, EventObserver{ id ->
            val action = DetailFragmentDirections.actionDetailFragmentToCaseFragment(id)
            navController.navigate(action)
        })
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)
        builder.setTitle(R.string.delete_question_title)
        builder.setMessage(R.string.delete_question_description)
        builder.setCancelable(true)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.deletePerson()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun finish() {
        navController.navigate(R.id.action_detailFragment_to_loginActivity)
        activity?.finish()
    }


}