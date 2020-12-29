package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import java.util.*


@AndroidEntryPoint
class BookTripFragment : BaseFragment<FragmentBookTripBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private val args: BookTripFragmentArgs by navArgs()

    private var petAllow = false
    private var havePet = false

    override fun getViewBinding(): FragmentBookTripBinding =
        FragmentBookTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else {
                createAlerter(it.message)
                findNavController()?.bottomNav(R.id.searchTripsFragment)
            }

        })

        viewModel.tripBook.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_bookTripFragment_to_successTripBookFragment)
        })

    }

    override fun setUpViews() {

        args.trip?.let { currentTrip ->
            with(binding) {
                from.text = currentTrip.destTo.title
                to.text = currentTrip.destFrom.title
                time.text = currentTrip.time
                day.text = currentTrip.date
                seats.setUpperLimit(currentTrip.vacancies)
                price.text = getString(R.string.price, currentTrip.price.toString())
                petAllow = currentTrip.hasPet


                //Check if the trips allows pet
                if (currentTrip.hasPet) {
                    petsSwitch.setOnCheckedChangeListener { _, b ->
                        //Listener for user if wants pet on the trip
                        if (b) {
                            havePetSwitchInfo.text = getString(R.string.yes)
                            havePet = true
                        } else {
                            havePetSwitchInfo.text = getString(R.string.no)
                            havePet = false
                        }
                    }
                } else {
                    petsSwitch.setOnCheckedChangeListener { _, _ ->
                        createToast(getString(R.string.driver_no_pets_allowed))
                        havePet = false
                        petsSwitch.isChecked = false
                    }
                }
                backButton.setOnClickListener {
                    onBackPressed()
                }

                book.setOnClickListener {

                    var list: ArrayList<String> = ArrayList()
                    currentTrip.passengers.forEach {
                        list.add(it.user.id)
                    }
                    viewModel.bookTrip(currentTrip.id, binding.seats.currentNum, havePet, (activity as SearchTripActivity).localUser.id, list)
                }
            }
        }

    }


}