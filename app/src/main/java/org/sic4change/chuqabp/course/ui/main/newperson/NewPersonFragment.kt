package org.sic4change.chuqabp.course.ui.main.newperson

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
import kotlinx.android.synthetic.main.fragment_new_person.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.course.ui.common.CropQuality.Companion.IMAGE_ASPECT_RATIO_X_Y
import org.sic4change.chuqabp.course.ui.common.CropQuality.Companion.IMAGE_COMPRESS_QUALITY
import org.sic4change.chuqabp.course.ui.main.detail.DetailFragmentDirections
import org.sic4change.chuqabp.databinding.FragmentNewPersonBinding


class NewPersonFragment : Fragment() {

    private var binding: FragmentNewPersonBinding? = null

    private lateinit var navController: NavController

    private val viewModel : NewPersonViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_new_person, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = view.findNavController()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@NewPersonFragment
        }

        ivPhotoPerson.setOnClickListener {
            activity?.let { _ ->
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(IMAGE_COMPRESS_QUALITY)
                    .setAspectRatio(IMAGE_ASPECT_RATIO_X_Y, IMAGE_ASPECT_RATIO_X_Y)
                    .start(activity!!.applicationContext, this)
            }
        }

        btnCreatePerson.setOnClickListener {
            viewModel.onCreatePersonClicked(etName.text.toString(), etSurnames.text.toString(), etBirthday.text.toString(),
                etPhone.text.toString(), etEmail.text.toString(), viewModel.photoUrl.value.toString(), etLocation.text.toString())
        }

        btnCancelPerson.setOnClickListener {
            val action = NewPersonFragmentDirections.actionNewPersonFragmentToPersons()
            navController.navigate(action)
        }

        viewModel.currentLocation.observe(this, Observer<String> {
            etLocation.setText(it)
        })

        viewModel.showingCreatePersonError.observe(this, EventObserver {
            if (it) {
                showMessage(getString(R.string.person_mandatory_field))
            } else {
                val action = NewPersonFragmentDirections.actionNewPersonFragmentToNewCase(viewModel.personId.value.toString())
                navController.navigate(action)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                ivPhotoPerson.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                result.uri.path?.let { viewModel.setPhotoUrl(it) }
                Glide.with(this).load(result.uri).into(ivPhotoPerson)
            }
        }
    }

    private fun showMessage(message: String) {
        snackbar(binding?.root, message)
    }

}