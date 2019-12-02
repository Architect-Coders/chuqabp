package org.sic4change.chuqabp.course.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.Case
import org.sic4change.chuqabp.course.ui.common.loadUrl

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    companion object {
        const val CASE = "DetailActivity:case"
    }

    private val presenter = DetailPresenter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter.onCreate(this, intent.getParcelableExtra(CASE))
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(case: Case) = with(case) {
        caseDetailToolbar.title = "${name} ${surnames}"
        caseDetailImage.loadUrl(photo)
        caseDetailInfo.setCase(this)
    }


}
