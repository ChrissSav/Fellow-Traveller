package gr.fellow.fellow_traveller.ui.home.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.home.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private var progress = 0f

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)


    @SuppressLint("SetTextI18n")
    override fun setUpObservers() {
        /*  viewModel.user.observe(viewLifecycleOwner, {
              //Delete the "ς" or "s" when we display the name
              if (it.firstName.takeLast(1) == "ς")
                  binding.userName.text = it.firstName.dropLast(1) + ","
              else
                  binding.userName.text = it.firstName + ","

          })*/
    }

    override fun setUpViews() {

        /* binding.motion.progress = progress

         binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
             override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
             }

             override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

             }

             override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                 progress = p0?.progress ?: 0f
             }

             override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
             }

         })

         binding.textViewInfo.setOnClickListener {
             activity?.openUrl("https://covid19.gov.gr")
         }

         binding.constraintLayoutNew.setOnClickListener {
             startActivityForResult(NewTripActivity::class, 1, bundleOf("userRate" to viewModel.user.value?.rate, "localUser" to viewModel.user.value!!))
         }

         binding.newTrip.setOnClickListener {
             startActivityForResult(NewTripActivity::class, 1, bundleOf("userRate" to viewModel.user.value?.rate, "localUser" to viewModel.user.value!!))
         }

         binding.constraintLayoutSearch.setOnClickListener {
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("localUser" to viewModel.user.value!!))

         }

         binding.searchTrip.setOnClickListener {
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("localUser" to viewModel.user.value!!))

         }

         binding.help.setOnClickListener {
             activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
         }
         binding.guide.setOnClickListener {
             activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
         }
         binding.searchAndOffer.setOnClickListener {
             activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
         }

         binding.cityChoice1.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FSKG%20-%20Athens.jpg?alt=media&token=6ed5258b-c268-4fc5-8236-c9af098d187e")
         binding.cityChoice2.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA%20-%20ATH%202.jpg?alt=media&token=e7dca60f-cc8f-416f-9639-c9a0233b428a")
         binding.cityChoice3.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA%20-%20PAT%202%20.jpg?alt=media&token=edefdd27-078c-4524-bce9-5c498c120fe1")
         binding.cityChoice4.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FKAB%20-%20SKG.jpg?alt=media&token=39ba040f-9705-445c-a2a6-c8b478406133")

         binding.cityChoice1.setOnClickListener {
             val placeFrom = PlaceModel("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki), 40.634781.toFloat(), 22.943090.toFloat())
             val placeTo = PlaceModel("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens), 37.97534.toFloat(), 23.736151.toFloat())
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo, "localUser" to viewModel.user.value))
         }

         binding.cityChoice2.setOnClickListener {
             val placeFrom = PlaceModel("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.placeholder_city_ioannina), 39.674530.toFloat(), 20.840210.toFloat())
             val placeTo = PlaceModel("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens), 37.97534.toFloat(), 23.736151.toFloat())
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo, "localUser" to viewModel.user.value))
         }

         binding.cityChoice3.setOnClickListener {
             val placeFrom = PlaceModel("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.placeholder_city_ioannina), 39.674530.toFloat(), 20.840210.toFloat())
             val placeTo = PlaceModel("ChIJLe0kpZk1XhMRoIy54iy9AAQ", getString(R.string.placeholder_city_patra), 38.246639.toFloat(), 21.734573.toFloat())
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo, "localUser" to viewModel.user.value))
         }

         binding.cityChoice4.setOnClickListener {
             val placeFrom = PlaceModel("ChIJAfxmkHK7rhQRbEdqRDfhZ_U", getString(R.string.placeholder_city_kavala), 40.937607.toFloat(), 24.412866.toFloat())
             val placeTo = PlaceModel("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki), 40.634781.toFloat(), 22.943090.toFloat())
             startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo, "localUser" to viewModel.user.value))
         }*/
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                   // viewModel.addTripCreate(it)
                }
            }

        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    // viewModel.addTripPassenger(it)
                }
            }

        }
    }


}