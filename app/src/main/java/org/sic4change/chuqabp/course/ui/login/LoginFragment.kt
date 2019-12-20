package org.sic4change.chuqabp.course.ui.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.UserRepository
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var viewModel : LoginViewModel

    private lateinit var binding : FragmentLoginBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false)
        binding.setLifecycleOwner(viewLifecycleOwner)
        viewModel = getViewModel{ LoginViewModel(UserRepository(activity!!.app)) }
        binding.viewModel = viewModel

        viewModel.model.observe(this, Observer {
            //::updateUI
            updateUI(viewModel.model.value!!)
        })

        binding.btnLogin.setOnClickListener {
            login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.tvSignUp.setOnClickListener {
            viewModel.onCreateAccountClicked()
        }

       binding.etPassword.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                true
            }
            false
        }

        binding.tvResetPassword.setOnClickListener {
            forgotPassword(binding.etEmail.text.toString())
        }

        binding.tvTermsAndConditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }

        return binding.root
    }

    private fun updateUI(model: LoginViewModel.UIModel) {
        if (model == LoginViewModel.UIModel.Loading) disableLoginView() else enableLoginView()
        when(model) {
            is LoginViewModel.UIModel.Enabled -> enableLoginView()
            is LoginViewModel.UIModel.NavigationToMain -> goToMainActivity()
            is LoginViewModel.UIModel.NavigationToCreateAccount -> goToCreateAccount()
            is LoginViewModel.UIModel.ShowingForgotPasswordResult -> {
                if (model.result) showMessage(getString(R.string.forgot_password_ok))
                else showMessage(getString(R.string.forgot_password_error))
            }
            is LoginViewModel.UIModel.ShowingLoginErrors -> showMessage(model.message)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.onLoginClicked(email, password)
    }

    private fun forgotPassword(email: String) {
        viewModel.onForgotPasswordClicked(email)
    }

    fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    fun disableLoginView() {
        binding.btnLogin.isClickable = false
        binding.btnLogin.isEnabled = false
        binding.tvTermsAndConditions.isClickable = false
        binding.tvResetPassword.isClickable = false
        binding.etPassword.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.tvNewUser.isClickable = false
    }

    fun enableLoginView() {
        binding.btnLogin.isClickable = true
        binding.btnLogin.isEnabled = true
        binding.tvTermsAndConditions.isClickable = true
        binding.tvResetPassword.isClickable = true
        binding.etPassword.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.tvNewUser.isClickable = true
    }

    fun goToMainActivity() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        activity?.finish()
    }

    fun goToCreateAccount() {
        findNavController().navigate(R.id.action_login_to_createAccount)
    }

}