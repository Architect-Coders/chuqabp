package org.sic4change.chuqabp.course.ui.main.newcase

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_new_case.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.data.AndroidPermissionChecker
import org.sic4change.chuqabp.course.data.PlayServicesLocationDataSource
import org.sic4change.chuqabp.course.data.database.RoomDataSource
import org.sic4change.chuqabp.course.data.server.FirebaseDataSource
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.course.ui.common.CropQuality.Companion.IMAGE_ASPECT_RATIO_X_Y
import org.sic4change.chuqabp.course.ui.common.CropQuality.Companion.IMAGE_COMPRESS_QUALITY
import org.sic4change.chuqabp.databinding.FragmentNewCaseBinding
import org.sic4change.data.repository.RegionRepository
import org.sic4change.usescases.CreateCase
import org.sic4change.usescases.GetLocation


class NewCaseFragment : Fragment() {

    private var binding: FragmentNewCaseBinding? = null

    private lateinit var navController: NavController

    private val viewModel : NewCaseViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_new_case, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = view.findNavController()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@NewCaseFragment
        }

        ivPhotoCase.setOnClickListener {
            activity?.let { it1 ->
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(IMAGE_COMPRESS_QUALITY)
                    .setAspectRatio(IMAGE_ASPECT_RATIO_X_Y, IMAGE_ASPECT_RATIO_X_Y)
                    .start(activity!!.applicationContext, this)
            }
        }

        btnCreateCase.setOnClickListener {
            viewModel.onCreateCaseClicked(etName.text.toString(), etSurnames.text.toString(), etBirthday.text.toString(),
                etPhone.text.toString(), etEmail.text.toString(), viewModel.photoUrl.value.toString(), etLocation.text.toString())
        }

        viewModel.currentLocation.observe(this, Observer<String> {
            etLocation.setText(it)
        })

        viewModel.showingCreateCaseError.observe(this, EventObserver {
            if (it) {
                showMessage(getString(R.string.case_mandatory_field))
            } else {
                navController.navigate(R.id.action_newCaseFragment_to_loginActivity)
                activity?.finish()
            }
        })

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_add).setVisible(false)
        menu.findItem(R.id.action_edit).setVisible(false)
        menu.findItem(R.id.action_delete).setVisible(false)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                ivPhotoCase.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                result.uri.path?.let { viewModel.setPhotoUrl(it) }
                Glide.with(this).load(result.uri).into(ivPhotoCase)
            }
        }
    }

    private fun showMessage(message: String) {
        snackbar(binding?.root, message)
    }

}