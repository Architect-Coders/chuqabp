package org.sic4change.chuqabp.course.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.data.database.RoomDataSource
import org.sic4change.chuqabp.course.data.server.FirebaseDataSource
import org.sic4change.chuqabp.course.ui.common.app
import org.sic4change.chuqabp.course.ui.common.bindingInflate
import org.sic4change.chuqabp.course.ui.common.getViewModel
import org.sic4change.chuqabp.databinding.FragmentDetailBinding
import org.sic4change.usescases.FindCaseById

class DetailFragment: Fragment() {

    private lateinit var viewModel : DetailViewModel

    private var binding: FragmentDetailBinding? = null

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val casesRepository = org.sic4change.data.repository.CasesRepository(RoomDataSource(app.db), FirebaseDataSource())
        viewModel= getViewModel {
            DetailViewModel(args.id, FindCaseById(casesRepository))
        }

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }
    }

}