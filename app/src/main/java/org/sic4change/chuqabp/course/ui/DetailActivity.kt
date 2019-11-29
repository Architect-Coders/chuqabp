package org.sic4change.chuqabp.course.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.Case

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CASE = "DetailActivity:case"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        with(intent.getParcelableExtra<Case>(CASE)) {
            //caseDetailToolbar.title = "${name} ${surnames}"
            caseDetailImage.loadUrl(photo)
            caseDetailInfo.setCase(this)
        }
    }
}
