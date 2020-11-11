package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.PetBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel

@AndroidEntryPoint
class BookTripFragment : BaseFragment<FragmentBookTripBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var index: Int = 0
    private lateinit var currentTrip: TripSearch
    private lateinit var petBottomSheetDialog: PetBottomSheetDialog
    private var petAllow = false

    override fun getViewBinding(): FragmentBookTripBinding =
        FragmentBookTripBinding.inflate(layoutInflater)

    override fun handleIntent() {
        index = requireArguments().getInt("index")
    }


    override fun setUpObservers() {

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else {
                createAlerter(it.message)
                findNavController()?.popBackStack(R.id.searchTripsFragment, true)
            }

        })

        viewModel.tripBook.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_bookTripFragment_to_successTripBookFragment)
        })
    }

    override fun setUpViews() {

        viewModel.resultTrips.value?.get(index)?.let { tripSearch ->
            currentTrip = tripSearch
            with(binding) {
                from.text = currentTrip.destTo.title
                to.text = currentTrip.destFrom.title
                time.text = currentTrip.time
                day.text = currentTrip.date
                seats.setUpperLimit(currentTrip.vacancies)
                price.text = getString(R.string.price, currentTrip.price.toString())
                petAllow = currentTrip.hasPet
                if (!currentTrip.hasPet)
                    pet.setText(getString(R.string.no))
            }
        }
        with(binding) {

            backButton.setOnClickListener {
                onBackPressed()
            }

            book.setOnClickListener {
                if (pet.text.isNullOrEmpty()) {
                    createAlerter("Παρακαλώ επιλέξτε συνοδεία κατοικιδίου")
                } else {
                    val yes = getString(R.string.yes)
                    viewModel.bookTrip(currentTrip.id, binding.seats.currentNum, binding.pet.text.toString() == yes)
                }
            }

            pet.setOnClickListener {
                if (petAllow) {
                    petBottomSheetDialog = PetBottomSheetDialog(this@BookTripFragment::onPetItemClickListener)
                    petBottomSheetDialog.show(childFragmentManager, "petBottomSheetDialog")
                }
            }
        }
    }

    private fun onPetItemClickListener(petAnswerType: PetAnswerType) {
        if (petAnswerType == PetAnswerType.Yes) {
            binding.pet.setText(getString(R.string.yes))
        } else {
            binding.pet.setText(getString(R.string.no))
        }
    }

}