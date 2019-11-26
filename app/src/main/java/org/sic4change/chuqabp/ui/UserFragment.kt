package org.sic4change.chuqabp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.databinding.FragmentUserBinding
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.utils.loadPhotoUrl
import org.sic4change.chuqabp.viewmodel.MainViewModel
import org.sic4change.chuqabp.viewmodel.MainViewModelFactory

class UserFragment: Fragment() {


    private lateinit var binding : FragmentUserBinding


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MainViewModelFactory(activity.application))
            .get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)
        setHasOptionsMenu(true)
        //(activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_delete)
        viewModel.user.observe(this, Observer<Models.User> {
            if (it != null && it.email.isNotEmpty()) {
                showUser(viewModel.user.value)
            }
        })

        return binding.root
    }

    private fun showUser(user: Models.User?) {
        binding.ivProfileUser.loadPhotoUrl(user?.photo)
        binding.tvNameAndSurname.text = "${user?.name} ${user?.surnames}"
        binding.tvProfileEmail.text = user?.email
    }


}