package org.sic4change.chuqabp.utils

import android.app.AlertDialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import org.sic4change.chuqabp.R


class Sizes {

    companion object  {

        var action_bar_photo_dimens = 50

        fun convertDpToPixel(dp: Int, context: Context): Int {
            return  dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

            //return dp * (context.resources.displayMetrics.densityDpi as Float / DisplayMetrics.DENSITY_DEFAULT)
        }


        fun convertPixelsToDp(px: Float, context: Context): Float {
            return px / (context.resources.displayMetrics.densityDpi as Float / DisplayMetrics.DENSITY_DEFAULT)
        }

    }




}

