package org.sic4change.chuqabp.course.ui.main.closecase

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_close_case.*

import org.koin.core.parameter.parametersOf
import org.sic4change.chuqabp.databinding.FragmentCloseCaseBinding

class CloseCaseFragment: Fragment() {


    private lateinit var closedReasonAdapter: ClosedReasonAdapter

    private var binding: FragmentCloseCaseBinding? = null

    private lateinit var navController: NavController


    private val args: CloseCaseFragmentArgs by navArgs()

    private val viewModel : CloseCaseViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_close_case, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        navController = view.findNavController()

        closedReasonAdapter = ClosedReasonAdapter(viewModel::onClosedReasonClicked)
        recycler_reasons.adapter = closedReasonAdapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@CloseCaseFragment
        }

        viewModel.closeReasonSelected.observe(this, Observer<String> {
            btnCloseCase.isEnabled = it != null
            btnCloseCase.isClickable = it != null
            closedReasonAdapter.notifyDataSetChanged()
        })

        btnCloseCase.setOnClickListener {
            viewModel.onCloseCaseClicked(etDescription.text.toString())
            navController.navigate(R.id.action_closeCaseFragment_to_cases)
        }

        btnCancelCloseCase.setOnClickListener {
            navController.navigate(R.id.action_closeCaseFragment_to_cases)
        }

    }


}