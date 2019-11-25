package org.sic4change.chuqabp.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.domain.Models
import org.sic4change.chuqabp.utils.Sizes
import org.sic4change.chuqabp.viewmodel.MainViewModel
import org.sic4change.chuqabp.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MainViewModelFactory(activity.application))
            .get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getUser()
        viewModel.user.observe(this, Observer<Models.User> {
            if (it != null && it.email.isNotEmpty()) {
               showUserImage(it.photo)
            }
        })
    }

    private fun showUserImage(photoUrl: String) {
        Glide.with(this).asDrawable().load(photoUrl).apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_account_circle).override(Sizes.convertDpToPixel(Sizes.action_bar_photo_dimens, this),
                Sizes.convertDpToPixel(Sizes.action_bar_photo_dimens, this)).into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(resource: Drawable, @Nullable transition: Transition<in Drawable?>?) {
                    supportActionBar!!.setHomeAsUpIndicator(resource)
                }
                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
            })
    }


}
