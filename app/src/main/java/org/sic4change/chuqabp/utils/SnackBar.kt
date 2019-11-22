package org.sic4change.chuqabp.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Methods to show snackbar
 */
fun Context.snackbar (view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, length).show()
}

fun Fragment.snackbar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    context?.snackbar(view, message, length)
}
