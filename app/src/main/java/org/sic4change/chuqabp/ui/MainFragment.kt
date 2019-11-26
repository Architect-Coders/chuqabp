package org.sic4change.chuqabp.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.databinding.FragmentMainBinding
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.utils.Sizes
import org.sic4change.chuqabp.viewmodel.MainViewModel
import org.sic4change.chuqabp.viewmodel.MainViewModelFactory
import timber.log.Timber

class MainFragment: Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MainViewModelFactory(activity.application))
            .get(MainViewModel::class.java)
    }



    private lateinit var binding : FragmentMainBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)
        setHasOptionsMenu(true)
        viewModel.getUser()
        viewModel.user.observe(this, Observer<Models.User> {
            if (it != null && it.email.isNotEmpty()) {
                showUserImage(it.photo)
            }
        })
        return binding.root
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem?.getItemId() == android.R.id.home) {
            goToUserView()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun goToUserView() {
        findNavController().navigate(R.id.action_main_to_user)
    }

    private fun showUserImage(photoUrl: String) {
        Glide.with(this).asDrawable().load(photoUrl).apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_account_circle).override(
                Sizes.convertDpToPixel(Sizes.action_bar_photo_dimens, activity!!.applicationContext),
                Sizes.convertDpToPixel(Sizes.action_bar_photo_dimens, activity!!.applicationContext)).into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(resource: Drawable, @Nullable transition: Transition<in Drawable?>?) {
                    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(resource)
                }
                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
            })
    }

}