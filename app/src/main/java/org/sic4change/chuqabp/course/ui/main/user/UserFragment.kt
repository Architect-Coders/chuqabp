package org.sic4change.chuqabp.course.ui.main.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.Event
import org.sic4change.chuqabp.course.ui.common.TermsAndConditions
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.common.snackbar
import org.sic4change.chuqabp.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private var binding: FragmentUserBinding? = null

    private lateinit var navController: NavController

    private val viewModel : UserViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_user, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_refresh).isVisible = false
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_account).isVisible = false
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_edit).isVisible = false
        super.onPrepareOptionsMenu(menu)
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

}