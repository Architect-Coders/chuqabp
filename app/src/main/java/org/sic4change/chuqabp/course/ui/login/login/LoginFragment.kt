package org.sic4change.chuqabp.course.ui.login.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.data.UserRepository
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.course.ui.login.LoginViewModel
import org.sic4change.chuqabp.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var viewModel : LoginViewModel

    private lateinit var binding : FragmentLoginBinding

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container!!.bindingInflate(R.layout.fragment_login, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel = getViewModel{
            LoginViewModel(UserRepository(activity!!.app))
        }

        viewModel.showingLoginErrors.observe(viewLifecycleOwner, EventObserver { message ->
            showMessage(message)
        })

        viewModel.showingForgotPasswordResult.observe(viewLifecycleOwner, EventObserver { result ->
            if (result) {
                showMessage(getString(R.string.forgot_password_ok))
                enableLoginView()
            } else {
                showMessage(getString(R.string.forgot_password_error))
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                disableLoginView()
            } else {
                enableLoginView()
            }
        })

        viewModel.navigateToCreateAccount.observe(this, EventObserver {
            goToCreateAccount()
        })

        viewModel.navigateToMain.observe(this, EventObserver {
            goToMainActivity()
        })

        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@LoginFragment
        }

        btnLogin.setOnClickListener {
            login(etEmail.text.toString(), etPassword.text.toString())
        }

        tvSignUp.setOnClickListener {
            viewModel.onCreateAccountClicked()
        }

       etPassword.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                login(etEmail.text.toString(), etPassword.text.toString())
            }
            false
        }

        tvResetPassword.setOnClickListener {
            forgotPassword(etEmail.text.toString())
        }

        tvTermsAndConditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.onLoginClicked(email, password)
    }

    private fun forgotPassword(email: String) {
        viewModel.onForgotPasswordClicked(email)
    }

    private fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    private fun disableLoginView() {
        btnLogin.isClickable = false
        btnLogin.isEnabled = false
        tvTermsAndConditions.isClickable = false
        tvResetPassword.isClickable = false
        etPassword.isEnabled = false
        etEmail.isEnabled = false
        tvNewUser.isClickable = false
    }

    private fun enableLoginView() {
        btnLogin.isClickable = true
        btnLogin.isEnabled = true
        tvTermsAndConditions.isClickable = true
        tvResetPassword.isClickable = true
        etPassword.isEnabled = true
        etEmail.isEnabled = true
        tvNewUser.isClickable = true
    }

    private fun goToMainActivity() {
        navController.navigate(R.id.action_loginFragment_to_mainActivity)
        activity?.finish()
    }

    private fun goToCreateAccount() {
        navController.navigate(R.id.action_login_to_createAccount)
    }

}