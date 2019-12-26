package org.sic4change.chuqabp.course.ui.login.create

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_create_account.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.UserRepository
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.chuqabp.databinding.FragmentCreateAccountBinding


class CreateAccountFragment: Fragment() {

    private lateinit var viewModel : LoginViewModel

    private lateinit var binding : FragmentCreateAccountBinding

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container!!.bindingInflate(R.layout.fragment_create_account, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        navController = view.findNavController()

        viewModel = getViewModel{
            LoginViewModel(
                UserRepository(activity!!.app)
            )
        }

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@CreateAccountFragment
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer<Boolean> {
            enableCreateAccountView()
            if (it) disableCreateAccountView() else enableCreateAccountView()
        })

        viewModel.navigateToMain.observe(this, EventObserver{
            goToMainActivity()
        })

        viewModel.showingCreateAccountErrors.observe(viewLifecycleOwner, EventObserver { message ->
            showMessage(message)
        })

        tvTermsAndConditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }

        btnCreateAccount.setOnClickListener {
            createAccount(etEmail.text.toString(), etPassword.text.toString())
        }

        etPassword.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                createAccount(etEmail.text.toString().trim(), etPassword.text.toString().trim())
            }
            false
        }
    }

    private fun createAccount(email: String, password: String) {
        if (cbTerms.isChecked) {
            viewModel.onCreateNewUserClicked(email, password)
        } else {
            showMessage(getString(R.string.error_terms_and_conditios))
        }
    }

    private fun disableCreateAccountView() {
        btnCreateAccount.isClickable = false
        tvTermsAndConditions.isClickable = false
        etPassword.isEnabled = false
        etEmail.isEnabled = false
        cbTerms.isEnabled = false
    }

    private fun enableCreateAccountView() {
        btnCreateAccount.isClickable = true
        tvTermsAndConditions.isClickable = true
        etPassword.isEnabled = true
        etEmail.isEnabled = true
        cbTerms.isEnabled = true
    }

    private fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    private fun goToMainActivity() {
        navController.navigate(R.id.action_createAccountFragment_to_mainActivity)
        activity?.finish()
    }


}
