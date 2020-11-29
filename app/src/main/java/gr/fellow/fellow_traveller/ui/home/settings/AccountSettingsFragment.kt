package gr.fellow.fellow_traveller.ui.home.settings

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.iceteck.silicompressorr.FileUtils
import com.iceteck.silicompressorr.SiliCompressor
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountSettingsBinding
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.UserImagePickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class AccountSettingsFragment : BaseFragment<FragmentAccountSettingsBinding>() {

    @Inject
    lateinit var connectivityHelper: ConnectivityHelper
    private val viewModel: HomeViewModel by activityViewModels()
    private var mImageUri: Uri? = null
    private var mStorageRef: StorageReference? = null
    private var tempImageFile: File? = null
    private var newImage = ""
    private lateinit var userImagePickBottomSheetDialog: UserImagePickBottomSheetDialog


    override fun getViewBinding(): FragmentAccountSettingsBinding =
        FragmentAccountSettingsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding) {
                picture.loadImageFromUrl(it.picture)
                firstName.text = it.firstName
                lastName.text = it.lastName
                email.text = it.email
                aboutMe.setText(it.aboutMe)
                picture.clearFocus()
                firstName.clearFocus()
                lastName.clearFocus()
                email.clearFocus()
                aboutMe.clearFocus()
            }


        })

        viewModel.successUpdateInfo.observe(viewLifecycleOwner, Observer {
            createToast(getString(R.string.profile_update_success))
            onBackPressed()
        })

    }


    override fun setUpViews() {

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        mStorageRef = FirebaseStorage.getInstance().getReference("profile_images")

        binding.uploadImage.setOnClickListener {
            userImagePickBottomSheetDialog = UserImagePickBottomSheetDialog(this@AccountSettingsFragment::onImageSelect)
            userImagePickBottomSheetDialog.show(childFragmentManager, "userImagePickBottomSheetDialog")

        }


        binding.saveButton.setOnClickListener {


            if (binding.lastName.isCorrect() and binding.firstName.isCorrect()) {
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                viewModel.updateAccountInfo(firstName, lastName, binding.aboutMe.text.toString().trim())
            }
        }


    }


    private fun onChooseFile() {
        //CropImage.activity().start()
        val intent = CropImage.activity()
            .setAspectRatio(1, 1)
            .getIntent(requireContext())

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
    }

/*    //Get the extension of the image
    private fun getFileExtension(uri: Uri): String? {
        val cR: ContentResolver = (requireContext().contentResolver as ContentResolver)//getContentResolver()
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data) ///sas
            if (resultCode === RESULT_OK) {
                mImageUri = result.uri
                //profilePicture.setImageURI(mImageUri);
                //imageProgressBar.setVisibility(View.VISIBLE)
                //uploadImage();

                binding.progressBar.visibility = View.VISIBLE
                compressUriImage()
            } else if (resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // val e = result.error
                createToast(getString(R.string.error_msg))
            }
        }
    }

    private fun compressUriImage() {
        if (mImageUri != null) {
            tempImageFile = File(
                SiliCompressor.with(this.context)
                    .compress(
                        FileUtils.getPath(this.context, mImageUri), File(
                            requireActivity().cacheDir,
                            "temp"
                        )
                    )
            )
            val compressedUri = Uri.fromFile(tempImageFile)
            Log.i("Compress", "fhfjxffghdk")
            uploadImage(compressedUri)
            viewModel.setLoad(true)
        }
    }

    private fun uploadImage(imageUri: Uri?) {

        //Storage the image in Firebase
        if (imageUri != null) {
            //TODO possibility for same name if user changed his time or name of image
            //TODO dont upload large images
            val fileReference = mStorageRef!!.child("user-" + viewModel.user.value?.id.toString())
            fileReference.putFile(imageUri).addOnSuccessListener { _ ->
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    newImage = uri.toString()
                    tempImageFile!!.delete()

                    viewModel.updateUserImage(newImage)
                    Log.i("ImageInside", newImage)
                    binding.progressBar.visibility = View.GONE
                    //loadImageToImageView()

                    //updateUserImageOnFirebase(uri.toString())
                    //updateUserPicture(uri.toString())

                    createToast(getString(R.string.image_upload_success))
                }
                //Log.i("Image", taskSnapshot.uploadSessionUri.toString())
            }.addOnFailureListener { e ->
                tempImageFile!!.delete()
                viewModel.setLoad(false)
                Toast.makeText(this.context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
                .addOnProgressListener { _ -> //We get progress from uploading the image file
//                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    // TODO add progress bar
                    //imageProgressBar.setProgress(progress.toInt())

                }
        } else {
            viewModel.setLoad(false)
            createToast(getString(R.string.image_upload_failure))
        }
    }

//    private fun loadImageToImageView() {
//        try {
//            binding.picture.loadImageFromUrl(newImage)
//            binding.progressBar.visibility = View.GONE
//        } catch (e: Exception) {
//            //  Block of code to handle errors
//        }
//    }

    //Upload or Delete Photo
    private fun onImageSelect(value: Boolean) {
        if (value)
            if (connectivityHelper.checkInternetConnection())
                askForPermission()
            else
                createToast(getString(R.string.internet_connection_failure))
        else
            if (connectivityHelper.checkInternetConnection())
                viewModel.updateUserImage(null)
            else
                createToast(getString(R.string.internet_connection_failure))
    }


    private fun askForPermission() {
        askPermission(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) {
            //open gallery
            onChooseFile()
        }.onDeclined { e ->
            if (e.hasDenied()) {
                Toast.makeText(activity, getString(R.string.account_settings_access_denied), Toast.LENGTH_LONG).show()
            }
            if (e.hasForeverDenied()) {

                AlertDialog.Builder(activity)
                    .setMessage(getString(R.string.account_settings_dialog_message))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        e.goToSettings()
                        Toast.makeText(activity, getString(R.string.account_settings_guid_message), Toast.LENGTH_LONG).show()
                    } //ask again
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }
        }
    }

}