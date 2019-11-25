package org.sic4change.chuqabp.utils


import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Methods to show ImageView
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}


