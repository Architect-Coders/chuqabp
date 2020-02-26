package org.sic4change.chuqabp.course.ui.main.updatecase

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_update_case.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.main.main.PersonsAdapter
import org.sic4change.chuqabp.databinding.FragmentUpdateCaseBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.koin.core.parameter.parametersOf
import org.sic4change.domain.Person
import java.util.*

class UpdateCaseFragment: Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var adapter : PersonsAdapter

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var timePickerDialog: TimePickerDialog

    private var binding: FragmentUpdateCaseBinding? = null

    private lateinit var navController: NavController


    private val args: UpdateCaseFragmentArgs by navArgs()

    private val viewModel : UpdateCaseViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_update_case, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        navController = view.findNavController()

        adapter = PersonsAdapter(viewModel::onPersonClicked)
        recycler_persons_selector.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@UpdateCaseFragment
        }

        cvPerson.setOnClickListener {
            viewModel.setPersonSelected(false)
        }

        cvDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(this.context!!, R.style.DateTimeDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val month = monthOfYear + 1
                    viewModel.setDate("$dayOfMonth/$month/$year")
                    tvDate.text = "$dayOfMonth/$month/$year"
                }, year, month, day)

            datePickerDialog.show()
        }

        cvTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this.context!!, R.style.DateTimeDialogTheme,
                TimePickerDialog.OnTimeSetListener { view, hour, minute  ->
                    viewModel.setHour("$hour:$minute")
                    tvTime.text = "$hour:$minute"
                }, hour, minute, true)

            timePickerDialog.show()

        }

        cvPysical.setOnCheckedChangeListener { compoundButton, b ->
            setType()
        }

        cvSexual.setOnCheckedChangeListener { compoundButton, b ->
            setType()
        }

        cvPsychological.setOnCheckedChangeListener { compoundButton, b ->
            setType()
        }

        cvSocial.setOnCheckedChangeListener { compoundButton, b ->
            setType()
        }

        cvEconomic.setOnCheckedChangeListener { compoundButton, b ->
            setType()
        }

        etHow.addTextChangedListener {
            if (it != null && it.isNotEmpty()) {
                enabledRegister()
                ivSixStep.setImageResource(R.drawable.ic_check)
            } else {
                disabledRegister()
                ivSixStep.setImageResource(R.drawable.ic_looks_6)
            }
        }

        viewModel.personSelected.observe(this, Observer<Boolean> {
            if (!it) recycler_persons_selector.visibility = VISIBLE else recycler_persons_selector.visibility = GONE
        })

        viewModel.description.observe(this, Observer<String> {
            ivSixStep.setImageResource(R.drawable.ic_check)
            etHow.setText(it)
            enabledRegister()
        })

        btnUpdateCase.setOnClickListener {
            viewModel.onUpdateCaseClicked(viewModel.case.value?.id, viewModel.person.value?.id, viewModel.person.value?.name,
                viewModel.person.value?.surnames, tvDate.text.toString(), tvTime.text.toString(),
                etPlace.text.toString(), cvPysical.isChecked, cvSexual.isChecked, cvPsychological.isChecked,
                cvSocial.isChecked, cvEconomic.isChecked, etHow.text.toString())
            navController.navigate(R.id.action_updateCaseFragment_to_loginActivity)
        }

        viewModel.person.observe(this, Observer<Person> {
            tvPersonName.text = "${it.name} ${it.surnames}"
            ivOneStep.setImageResource(R.drawable.ic_check)
            enabledDayQuestion()
        })

        viewModel.date.observe(this, Observer<String> {
            ivTwoStep.setImageResource(R.drawable.ic_check)
            enabledHourQuestion()
        })

        viewModel.hour.observe(this, Observer<String> {
            ivThreeStep.setImageResource(R.drawable.ic_check)
            enabledPlaceQuestion()
            if (!it.contains("null") && cvPlace.visibility == VISIBLE) {
                ivFourStep.setImageResource(R.drawable.ic_check)
                enabledTypeQuestion()
            }
        })

        viewModel.currentLocation.observe(this, Observer<String> {
            etPlace.setText(it)
        })

        viewModel.type.observe(this, Observer<Boolean> {
            if (it) {
                enabledHowQuestion()
                ivFiveStep.setImageResource(R.drawable.ic_check)
            } else {
                disabledHowQuestion()
                disabledRegister()
                ivFiveStep.setImageResource(R.drawable.ic_looks_5)
            }
        })

    }

    private fun setType() {
        viewModel.setType(cvPysical.isChecked || cvSexual.isChecked || cvPsychological.isChecked ||
                cvSocial.isChecked || cvEconomic.isChecked)
    }

    private fun showPersonSelection() {
        recycler_persons_selector.visibility = VISIBLE
    }

    private fun hidePersonSelection() {
        recycler_persons_selector.visibility = GONE
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

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }

    private fun enabledDayQuestion() {
        cvDate.visibility = VISIBLE
    }

    private fun enabledHourQuestion() {
        cvTime.visibility = VISIBLE
    }

    private fun enabledPlaceQuestion() {
        cvPlace.visibility = VISIBLE
    }

    private fun enabledTypeQuestion() {
        cvType.visibility = VISIBLE
    }

    private fun enabledHowQuestion() {
        cvHow.visibility = VISIBLE
    }

    private fun disabledHowQuestion() {
        cvHow.visibility = GONE
    }

    private fun enabledRegister() {
        btnUpdateCase.visibility = VISIBLE
    }

    private fun disabledRegister() {
        btnUpdateCase.visibility = GONE
    }


}