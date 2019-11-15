package org.sic4change.chuqabp.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Methods to show toast
 */
fun Context.toast(message: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: Int, length: Int = Toast.LENGTH_SHORT) {
    context?.toast(message, length)
}