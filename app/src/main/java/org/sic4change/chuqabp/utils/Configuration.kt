package org.sic4change.chuqabp.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import org.sic4change.chuqabp.R

class Configuration {

    companion object  {

        var urlTermsAndConditions = "https://www.sic4change.org/politica-de-privacidad"

        fun showTermsAndConditions(context: FragmentActivity?) {
            val builder = AlertDialog.Builder(context)
            val webView = LayoutInflater.from(context).inflate(R.layout.fragment_terms_and_conditions, null) as WebView
            webView.loadUrl(urlTermsAndConditions)
            builder.setView(webView)
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

    }


}

