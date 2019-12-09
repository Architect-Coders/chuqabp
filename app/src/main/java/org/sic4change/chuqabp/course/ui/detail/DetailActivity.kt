package org.sic4change.chuqabp.course.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.getViewModel
import org.sic4change.chuqabp.course.ui.common.loadUrl

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CASE = "DetailActivity:case"
    }

    private lateinit var viewModel : DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = getViewModel {DetailViewModel(intent.getParcelableExtra(CASE))}

        viewModel.model.observe(this, Observer {
            updateUI(viewModel.model.value!!)
        })
    }


    private fun updateUI(model: DetailViewModel.UIModel) = with(model.case) {
        caseDetailToolbar.title = "${name} ${surnames}"
        caseDetailImage.loadUrl(photo)
        caseDetailInfo.setCase(this)
    }


}
