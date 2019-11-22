package org.sic4change.chuqabp.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.databinding.FragmentLoginBinding
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.utils.Configuration
import org.sic4change.chuqabp.utils.hideKeyboard
import org.sic4change.chuqabp.utils.snackbar
import org.sic4change.chuqabp.viewmodel.LoginViewModel
import org.sic4change.chuqabp.viewmodel.LoginViewModelFactory

class LoginFragment: Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, LoginViewModelFactory(activity.application))
            .get(LoginViewModel::class.java)
    }

    private lateinit var binding : FragmentLoginBinding


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel

        //login when click in button login
        binding.btnLogin.setOnClickListener {
            login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        // go to create account when click in signup
        binding.tvSignUp.setOnClickListener {
            goToCreateAccount()
        }

        //login when click in keyboard enter when password is typed
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

        //observe login response
        viewModel.loginResponse.observe(this, Observer<Models.UserManagementResponse> { loginResponse ->
            if (loginResponse != null) {
                if (loginResponse.successful) {
                    getUser(loginResponse.email)
                } else {
                    showMessage(loginResponse.error)
                }
                enableLoginView()
            }
        })

        //observe change password response
        viewModel.changePasswordResponse.observe(this, Observer<Boolean> { changePasswordResponse ->
            if (changePasswordResponse != null) {
                if (changePasswordResponse) {
                    showMessage(getString(R.string.forgot_password_ok))
                } else {
                    showMessage(getString(R.string.forgot_password_error))
                }
            }
            enableLoginView()
        })

        //observe user
        viewModel.user.observe(this, Observer<Models.User> {
            if (it != null && it.email.isNotEmpty()) {
                goToMainActivity()
            }
        })

        binding.tvTermsAndConditions.setOnClickListener {
            Configuration.showTermsAndConditions(activity)
        }

        return binding.root
    }

    /**
     * Method to login
     */
    private fun login(email: String, password: String) {
        disableLoginView()
        viewModel.login(email, password)
    }

    /**
     * Method to forgot password
     */
    private fun forgotPassword(email: String) {
        disableLoginView()
        viewModel.forgotPassword(email)
    }

    /**
     * Method to get user
     */
    private fun getUser(email: String) {
        disableLoginView()
        viewModel.getUser(email)
    }

    /**
     * Method to show messages using snackbar
     */
    fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    /**
     * Method to disable login view
     */
    fun disableLoginView() {
        binding.btnLogin.isClickable = false
        binding.btnLogin.isEnabled = false
        binding.tvTermsAndConditions.isClickable = false
        binding.tvResetPassword.isClickable = false
        binding.etPassword.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.tvNewUser.isClickable = false
    }

    /**
     * Method to enable login view
     */
    fun enableLoginView() {
        binding.btnLogin.isClickable = true
        binding.btnLogin.isEnabled = true
        binding.tvTermsAndConditions.isClickable = true
        binding.tvResetPassword.isClickable = true
        binding.etPassword.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.tvNewUser.isClickable = true
    }

    /**
     * Method to go to main activity
     */
    fun goToMainActivity() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        activity?.finish()
    }

    /**
     * Method to go to main activity
     */
    fun goToCreateAccount() {
        findNavController().navigate(R.id.action_login_to_createAccount)
    }


}