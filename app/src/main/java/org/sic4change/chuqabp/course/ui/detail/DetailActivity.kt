package org.sic4change.chuqabp.course.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.model.CasesRepository
import org.sic4change.chuqabp.course.ui.common.app
import org.sic4change.chuqabp.course.ui.common.getViewModel
import org.sic4change.chuqabp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CASE = "DetailActivity:case"
    }

    private lateinit var viewModel : DetailViewModel

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = getViewModel {DetailViewModel(intent.getStringExtra(CASE), CasesRepository(app))}
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

}
