package org.sic4change.chuqabp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.databinding.FragmentCreateAccountBinding
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.utils.TermsAndConditions
import org.sic4change.chuqabp.utils.snackbar
import org.sic4change.chuqabp.viewmodel.LoginViewModel
import org.sic4change.chuqabp.viewmodel.LoginViewModelFactory

class CreateAccountFragment: Fragment() {

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

    private lateinit var binding : FragmentCreateAccountBinding

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
            R.layout.fragment_create_account,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel

        binding.btnCreateAccount.setOnClickListener {
            createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString(),
                binding.etName.text.toString(), binding.etSurname.text.toString())
        }

        binding.tvTermsAndConditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }

        viewModel.createUserResponse.observe(this, Observer<Models.UserManagementResponse> { createUserResponse ->
            if (createUserResponse != null) {
                if (!createUserResponse.successful) {
                    showMessage(createUserResponse.error)
                }
                enableCreateAccountView()
            }

        })

        //observe user
        viewModel.user.observe(this, Observer<Models.User> {
            if (it != null && it.email.isNotEmpty()) {
                goToMainActiviy()
            }

        })
        return binding.root
    }

    /**
     * Method to createAccount
     */
    private fun createAccount(email: String, password: String, name: String, surnames: String) {
        disableCreateAccountView()
        if (checkCreateAccountValues()) {
            viewModel.createUser(email, password, name, surnames)
        } else {
            showMessage(getString(R.string.mandatory_field))
            enableCreateAccountView()
        }
    }

    /**
     * Method to check inputvalues
     */
    private fun checkCreateAccountValues() : Boolean {
        return binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()
                && binding.etName.text.isNotEmpty() && binding.etSurname.text.isNotEmpty()
                && binding.cbTerms.isChecked
    }

    /**
     * Method to disable create account view
     */
    fun disableCreateAccountView() {
        binding.btnCreateAccount.isClickable = false
        binding.tvTermsAndConditions.isClickable = false
        binding.etPassword.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.etName.isEnabled = false
        binding.etSurname.isEnabled = false
        binding.cbTerms.isEnabled = false
    }

    /**
     * Method to enable create account view
     */
    fun enableCreateAccountView() {
        binding.btnCreateAccount.isClickable = true
        binding.tvTermsAndConditions.isClickable = true
        binding.etPassword.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.etName.isEnabled = true
        binding.etSurname.isEnabled = true
        binding.cbTerms.isEnabled = true
    }

    /**
     * Method to show messages using snackbar
     */
    fun showMessage(message: String) {
        snackbar(binding.root, message)
    }

    /**
     * Method to go to main activity
     */
    fun goToMainActiviy() {
        findNavController().navigate(R.id.action_createAccountFragment_to_mainActivity)
        activity?.finish()
    }

}