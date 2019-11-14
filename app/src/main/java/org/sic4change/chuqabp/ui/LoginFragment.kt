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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.databinding.FragmentLoginBinding
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.extensions.hideKeyboard
import org.sic4change.chuqabp.viewmodel.LoginViewModel
import org.sic4change.chuqabp.viewmodel.LoginViewModelFactory
import timber.log.Timber

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
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as LoginActivity).supportActionBar?.title = getString(R.string.app_name)
    }

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
        //login when click in keyboard enter when password is typed
        binding.etPassword.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                true
            }
            false
        }

        viewModel.loginResponse?.observe(this, Observer<Models.LoginResponse> { loginResponse ->
            if (loginResponse != null) {
                if (loginResponse.logged) {
                    goToMainActivity()
                } else {
                    showLoginError(loginResponse.error)
                }
                enableLoginView()
            }
        })

        return binding.root
    }

    /**
     * Method to login
     */
    fun login(email: String, password: String) {
        disableLoginView()
        viewModel.login(email, password)
    }

    /**
     * Method to show login error
     */
    fun showLoginError(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
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
        this.findNavController().navigate(R.id.action_login_to_createAccount)
    }

}