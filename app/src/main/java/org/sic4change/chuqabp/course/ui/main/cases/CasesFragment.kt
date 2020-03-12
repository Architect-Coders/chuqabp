package org.sic4change.chuqabp.course.ui.main.cases

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_cases.*
import kotlinx.android.synthetic.main.fragment_cases.clFilter
import kotlinx.android.synthetic.main.fragment_cases.etNameSurnames
import kotlinx.android.synthetic.main.fragment_main.bttNavigation
import kotlinx.android.synthetic.main.fragment_main.recycler
import kotlinx.android.synthetic.main.fragment_main.swipe_container
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.EventObserver
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.common.hideKeyboard
import org.sic4change.chuqabp.databinding.FragmentCasesBinding
import ru.slybeaver.slycalendarview.SlyCalendarDialog
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class CasesFragment: Fragment(), SlyCalendarDialog.Callback {

    private lateinit var adapter : CasesAdapter

    private var binding: FragmentCasesBinding? = null

    private lateinit var navController: NavController


    private val viewModel : CasesViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            initView()
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
            initView()
            when (it.itemId) {
                R.id.new_case -> {
                    val action = CasesFragmentDirections.actionCasesFragmentToNewCaseFragement("")
                    navController.navigate(action)
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
            initView()
            val action = CasesFragmentDirections.actionCasesFragmentToNewCaseFragement("")
            navController.navigate(action)
        }

        btnFilterCase.setOnClickListener {
            showHideFilter()
        }

        etNameSurnames.doOnTextChanged { _, _, _, _ ->
            callFilter()
        }

        etPlace.doOnTextChanged { _, _, _, _ ->
            callFilter()
        }

        etRangeDate.doOnTextChanged { _, _, _, _ ->
            callFilter()
        }

        cpPhysicFilter.setOnClickListener {
            viewModel.selectUnSelectPhysic()
        }

        cpPsicologicalFilter.setOnClickListener {
            viewModel.selectUnSelectPsicological()
        }

        cpSexualFilter.setOnClickListener {
            viewModel.selectUnSelectSexual()
        }

        cpEconomicFilter.setOnClickListener {
            viewModel.selectUnSelectEconomic()
        }

        cpSocialFilter.setOnClickListener {
            viewModel.selectUnSelectSocial()
        }

        swStatus.setOnToggleSwitchChangeListener { position, _ ->
            when (position) {
                0 -> {
                    viewModel.selectStatus("open")
                }
                2 -> {
                    viewModel.selectStatus("closed")
                }
                else -> {
                    viewModel.selectStatus("")
                }
            }

        }

        ivClose.setOnClickListener {
            swStatus.isSelected = true
        }

        ivOpen.setOnClickListener {
            swStatus.isSelected = false
        }

        swipe_container.setOnRefreshListener {
            initView()
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

        viewModel.cpPhysicSelected.observe(this, Observer<Boolean> {
            callFilter()
        })

        viewModel.cpSexualSelected.observe(this, Observer<Boolean> {
            callFilter()
        })

        viewModel.cpPsicologicalSelected.observe(this, Observer<Boolean> {
            callFilter()
        })

        viewModel.cpSocialSelected.observe(this, Observer<Boolean> {
            callFilter()
        })

        viewModel.cpEconomicSelected.observe(this, Observer<Boolean> {
            callFilter()
        })

        viewModel.statusSelected.observe(this, Observer<String> {
            callFilter()
        })

        etRangeDate.setOnClickListener {
            if (fragmentManager != null) {
                this.hideKeyboard()
                SlyCalendarDialog()
                    .setSingle(false)
                    .setHeaderColor(Color.parseColor("#00c6c6"))
                    .setSelectedColor(Color.parseColor("#00c6c6"))
                    .setCallback(this).show(fragmentManager!!, "")
            }
        }
        initView()

    }

    private fun callFilter() {
        viewModel.filterCases(etNameSurnames.text.toString(), etPlace.text.toString(), etRangeDate.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun initView() {
        etNameSurnames.text.clear()
        etPlace.text.clear()
        etRangeDate.text = ""
        viewModel.resetTypesSelected()
        cpPhysicFilter.setTextColor(resources.getColor(R.color.gray))
        cpSexualFilter.setTextColor(resources.getColor(R.color.gray))
        cpPsicologicalFilter.setTextColor(resources.getColor(R.color.gray))
        cpEconomicFilter.setTextColor(resources.getColor(R.color.gray))
        cpSocialFilter.setTextColor(resources.getColor(R.color.gray))
        swStatus.checkedTogglePosition = 1
        clFilter.visibility = View.GONE
        this.hideKeyboard()
    }

    private fun showHideFilter() {
        if (clFilter.visibility == View.VISIBLE) {
            initView()
            viewModel.refreshCases()
        } else {
            clFilter.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        bttNavigation.menu.findItem(R.id.cases).isChecked = true
    }

    override fun onDataSelected(firstDate: Calendar?, secondDate: Calendar?, hours: Int, minutes: Int) {
        try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFirst = dateFormat.format(firstDate?.time)
            val dateEnd = dateFormat.format(secondDate?.time)
            etRangeDate.text = "$dateFirst - $dateEnd"
        } catch (e: Exception){
            println("malformed range date")
        }
    }

    override fun onCancelled() {

    }

}