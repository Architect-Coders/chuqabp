package org.sic4change.chuqabp.course.ui.main.updateperson

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_update_person.*
import kotlinx.android.synthetic.main.fragment_update_person.etBirthday
import kotlinx.android.synthetic.main.fragment_update_person.etEmail
import kotlinx.android.synthetic.main.fragment_update_person.etLocation
import kotlinx.android.synthetic.main.fragment_update_person.etName
import kotlinx.android.synthetic.main.fragment_update_person.etPhone
import kotlinx.android.synthetic.main.fragment_update_person.etSurnames
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.sic4change.chuqabp.R
import org.sic4change.chuqabp.course.ui.common.*
import org.sic4change.chuqabp.databinding.FragmentUpdatePersonBinding

class UpdatePersonFragment: Fragment() {

    private var binding: FragmentUpdatePersonBinding? = null

    private lateinit var navController: NavController

    private val args: UpdatePersonFragmentArgs by navArgs()

    private val viewModel : UpdatePersonViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_update_person, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@UpdatePersonFragment
        }

        ivUpdatePhotoPerson.setOnClickListener {
            activity?.let { it1 ->
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(CropQuality.IMAGE_COMPRESS_QUALITY)
                    .setAspectRatio(
                        CropQuality.IMAGE_ASPECT_RATIO_X_Y,
                        CropQuality.IMAGE_ASPECT_RATIO_X_Y
                    )
                    .start(activity!!.applicationContext, this)
            }
        }

        btnUpdatePerson.setOnClickListener {
            viewModel.onUpdatePersonClicked(args.id, etName.text.toString(), etSurnames.text.toString(), etBirthday.text.toString(),
                etPhone.text.toString(), etEmail.text.toString(), viewModel.photoUrl.value.toString(), etLocation.text.toString())
        }

        viewModel.showingUpdatePersonError.observe(this, EventObserver {
            if (it) {
                showMessage(getString(R.string.person_mandatory_field))
            } else {
                navController.navigate(R.id.action_updateFragment_to_loginActivity)
                activity?.finish()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                ivUpdatePhotoPerson.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                result.uri.path?.let { viewModel.setPhotoUrl(it) }
                Glide.with(this).load(result.uri).into(ivUpdatePhotoPerson)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_refresh).isVisible = false
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_account).isVisible = false
        menu.findItem(R.id.action_edit).isVisible = false
        menu.findItem(R.id.action_delete).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    private fun showMessage(message: String) {
        snackbar(binding?.root, message)
    }


}