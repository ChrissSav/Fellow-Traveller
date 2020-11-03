package gr.fellow.fellow_traveller.ui.home.settings

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.iceteck.silicompressorr.FileUtils
import com.iceteck.silicompressorr.SiliCompressor
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountSettingsBinding
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.UserImagePickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import java.io.File


@AndroidEntryPoint
class AccountSettingsFragment : BaseFragment<FragmentAccountSettingsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var mImageUri: Uri? = null
    private var mStorageRef: StorageReference? = null
    private var tempImagefile: File? = null
    private var newImage = ""
    private lateinit var userImagePickBottomSheetDialog: UserImagePickBottomSheetDialog

    override fun getViewBinding(): FragmentAccountSettingsBinding =
        FragmentAccountSettingsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            postDelay(150) {
                with(binding) {
                    picture.loadImageFromUrl(it.picture)
                    firstName.text = it.firstName
                    lastName.text = it.lastName
                    email.text = it.email
                    it.messengerLink?.let { m ->
                        messengerLink.text = m
                    }
                    aboutMe.text = it.aboutMe
                }
            }
        })

        viewModel.successUpdateInfo.observe(viewLifecycleOwner, Observer {
            createToast("Επιτυχής αποθήκευση")
        })

    }


    override fun setUpViews() {

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        mStorageRef = FirebaseStorage.getInstance().getReference("profile_images");

        binding.uploadImage.setOnClickListener {
            userImagePickBottomSheetDialog = UserImagePickBottomSheetDialog(this@AccountSettingsFragment::onImageSelect)
            userImagePickBottomSheetDialog.show(childFragmentManager, "userImagePickBottomSheetDialog")

        }


        binding.saveButton.setOnClickListener {


            if (checkFields()) {
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                viewModel.updateAccountInfo(firstName, lastName, binding.messengerLink.text, binding.aboutMe.text)
            }
        }
    }

    private fun checkFields(): Boolean {
        if (binding.firstName.text.toString().length < 3) {
            createAlerter("Ελεγξε το πεδιο του ονόματος")
            return false
        }

        if (binding.lastName.text.toString().length < 3) {
            createAlerter("Ελεγξε το πεδιο του επωνύμου")
            return false
        }
        return true
    }


    private fun onChooseFile() {
        //CropImage.activity().start()
        val intent = CropImage.activity()
            .setAspectRatio(1, 1)
            .getIntent(requireContext())

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    //Get the extension of the image
    private fun getFileExtension(uri: Uri): String? {
        val cR: ContentResolver = (requireContext().contentResolver as ContentResolver)//getContentResolver()
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

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
                val e = result.error
                createToast("Error")
            }
        }
    }

    private fun compressUriImage() {
        if (mImageUri != null) {
            tempImagefile = File(
                SiliCompressor.with(this.context)
                    .compress(
                        FileUtils.getPath(this.context, mImageUri), File(
                            requireActivity().getCacheDir(),
                            "temp"
                        )
                    )
            )
            val compressedUri = Uri.fromFile(tempImagefile)
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
            fileReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    newImage = uri.toString()
                    tempImagefile!!.delete()

                    viewModel.updateUserImage(newImage)
                    Log.i("ImageInside", newImage)
                    binding.progressBar.visibility = View.GONE
                    //loadImageToImageView()

                    //updateUserImageOnFirebase(uri.toString())
                    //updateUserPicture(uri.toString())
                    createToast("Η φωτογραφία ανέβηκε επιτυχώς")
                }
                //Log.i("Image", taskSnapshot.uploadSessionUri.toString())
            }.addOnFailureListener { e ->
                tempImagefile!!.delete()
                viewModel.setLoad(false)
                Toast.makeText(this.context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
                .addOnProgressListener { taskSnapshot -> //We get progress from uploading the image file
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    // TODO add progress bar
                    //imageProgressBar.setProgress(progress.toInt())
                }
        } else {
            viewModel.setLoad(false)
            createToast("Η φωτογραφία που επιλέξατε δεν είναι έγκυρη")
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

    private fun onImageSelect(value: Boolean) {
        if (value)
            onChooseFile()
        else
            viewModel.updateUserImage(null)
    }
}