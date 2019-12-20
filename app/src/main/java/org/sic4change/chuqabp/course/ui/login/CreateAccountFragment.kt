package org.sic4change.chuqabp.course.ui.login

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_account.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.UserRepository
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.databinding.FragmentCreateAccountBinding


class CreateAccountFragment: Fragment() {

    private lateinit var viewModel : LoginViewModel

    private lateinit var binding : FragmentCreateAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToLogin()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            goToLogin()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_account,
            container,
            false)
        binding.setLifecycleOwner(viewLifecycleOwner)
        viewModel = getViewModel{ LoginViewModel(UserRepository(activity!!.app)) }
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        viewModel.model.observe(this, Observer {
            //::updateUI
            updateUI(viewModel.model.value!!)
        })

        binding.tvTermsAndConditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }

        binding.btnCreateAccount.setOnClickListener {
            createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.etPassword.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                true
            }
            false
        }
        return binding.root
    }
    
    private fun updateUI(model: LoginViewModel.UIModel) {
        if (model == LoginViewModel.UIModel.Loading) disableCreateAccountView() else enableCreateAccountView()
        when(model) {
            is LoginViewModel.UIModel.Enabled -> enableCreateAccountView()
            is LoginViewModel.UIModel.NavigationToMain -> goToMainActiviy()
            is LoginViewModel.UIModel.ShowingCreateAccountErrors -> showMessage(model.message)
        }
    }

    private fun createAccount(email: String, password: String) {
        if (cbTerms.isChecked) {
            viewModel.onCreateNewUserClicked(email, password)
        } else {
            showMessage(getString(R.string.error_terms_and_conditios))
        }
    }

    fun disableCreateAccountView() {
        binding.btnCreateAccount.isClickable = false
        binding.tvTermsAndConditions.isClickable = false
        binding.etPassword.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.cbTerms.isEnabled = false
    }

    fun enableCreateAccountView() {
        binding.btnCreateAccount.isClickable = true
        binding.tvTermsAndConditions.isClickable = true
        binding.etPassword.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.cbTerms.isEnabled = true
    }

    fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    fun goToMainActiviy() {
        findNavController().navigate(R.id.action_createAccountFragment_to_mainActivity)
        activity?.finish()
    }

    fun goToLogin() {
        findNavController().navigate(R.id.action_createAccount_to_login)
    }

}
