package org.sic4change.chuqabp.course.ui.main.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.bttNavigation
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.TermsAndConditions
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.common.snackbar
import org.sic4change.chuqabp.databinding.FragmentUserBinding

class UserFragment : Fragment(){

    private var binding: FragmentUserBinding? = null

    private lateinit var navController: NavController

    private val viewModel : UserViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_user, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
        navController = view.findNavController()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@UserFragment
        }

        lyTermsAndCoditions.setOnClickListener {
            TermsAndConditions.showTermsAndConditions(activity)
        }

        lyChangePassword.setOnClickListener{
            viewModel.onChangePasswordClicked()
            snackbar(binding?.root, getString(R.string.sent_instructions_to_change_password))
        }

        lyLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        lyDeleteAccount.setOnClickListener {
            showDeleteUserConfirmationDialog()
        }

        viewModel.logoutConfirmed.observe(this, Observer<Event<Boolean>> {
            navController.navigate(R.id.action_userFragment_to_loginActivity)
            activity?.finish()
        })

        viewModel.removeAccountConfirmed.observe(this, Observer<Event<Boolean>> {
            navController.navigate(R.id.action_userFragment_to_loginActivity)
            activity?.finish()
        })

        bttNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.new_case -> {
                    navController.navigate(R.id.action_userFragment_to_newCase)
                    true
                }
                R.id.cases -> {
                    navController.navigate(R.id.action_userFragment_to_casesFragement)
                    true
                }
                R.id.persons -> {
                    navController.navigate(R.id.action_userFragment_to_mainFragment)
                    true
                }
                R.id.training -> {
                    navController.navigate(R.id.action_userFragment_to_trainingFragment)
                    true
                }
                R.id.configuration -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bttNavigation.menu.findItem(R.id.configuration).isChecked = true
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.logout)
        builder.setMessage(R.string.logout_question)
        builder.setCancelable(true)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.onLogoutClicked()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun showDeleteUserConfirmationDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.delete_account)
        builder.setMessage(R.string.delete_account_first_question)
        builder.setCancelable(true)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.onDeleteUserClicked()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}