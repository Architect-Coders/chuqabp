package org.sic4change.chuqabp.utils


import android.widget.ImageView
import com.bumptech.glide.Glide
import org.sic4change.chuqabp.R

/**
 * Methods to show ImageView
 */
fun ImageView.loadPhotoUrl(url: String?) {
    Glide.with(context).load(url).placeholder(R.drawable.ic_camera).into(this)
}


