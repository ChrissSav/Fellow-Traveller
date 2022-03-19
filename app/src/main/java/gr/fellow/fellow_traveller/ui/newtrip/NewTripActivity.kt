package gr.fellow.fellow_traveller.ui.newtrip

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.ui.dialogs.DatePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.TimePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.BagsStatusPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener
import gr.fellow.fellow_traveller.utils.ADDRESS
import gr.fellow.fellow_traveller.utils.validateDateTimeDiffer


@AndroidEntryPoint
class NewTripActivity : BaseActivityViewModel<ActivityNewTripBinding, NewTripViewModel>(NewTripViewModel::class.java) {


    lateinit var localUser: LocalUser
    private var seatsNum = 1
    private var pricePerPerson = 0f
    private lateinit var destinationFrom: Destination
    private lateinit var destinationTo: Destination
    private lateinit var carPickBottomSheetDialog: CarPickBottomSheetDialog
    private lateinit var bagsStatusPickBottomSheetDialog: BagsStatusPickBottomSheetDialog
    private lateinit var dateDialog: DatePickerCustomDialog
    private lateinit var timeDialog: TimePickerCustomDialog
    private var carList = mutableListOf<Car>()
    private var descriptionMaxLength = 200


    override fun provideViewBinding(): ActivityNewTripBinding =
        ActivityNewTripBinding.inflate(layoutInflater)


    override fun handleIntent() {

        val tempDestinationFrom = intent.getParcelableExtra<Destination>("destinationFrom")
        if (tempDestinationFrom == null)
            finish()

        val tempDestinationTo = intent.getParcelableExtra<Destination>("destinationTo")
        if (tempDestinationTo == null)
            finish()

        val tempLocalUser = intent.getParcelableExtra<LocalUser>("localUser")
        if (tempLocalUser == null)
            finish()

        tempDestinationFrom?.let {
            destinationFrom = it
        }

        tempDestinationTo?.let {
            destinationTo = it
        }

        tempLocalUser?.let {
            localUser = it
        }
    }

    override fun setUpObservers() {

        binding.destinationFrom.text = destinationFrom.title
        binding.destinationTo.text = destinationTo.title




        viewModel.carList.observe(this, Observer { list ->
            if (list.isNullOrEmpty()) {
                createToast(getString(R.string.have_not_cars))
                finish()
            }
            if (list.size == 1)
                viewModel.setCar(list.first())
            carList.clear()
            carList.addAll(list)
        })

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.green)
            else
                createAlerter(it.message, R.color.blue_color)
        })

        viewModel.load.observe(this, Observer {
            if (it)
                binding.progressLoad.root.visibility = View.VISIBLE
            else
                binding.progressLoad.root.visibility = View.GONE

        })

        viewModel.destinationFrom.observe(this, Observer {
            binding.destinationFrom.setDestination(it)
        })

        viewModel.destinationTo.observe(this, Observer {
            binding.destinationTo.setDestination(it)
        })

        viewModel.destinationPickUp.observe(this, Observer {
            binding.pickUpDestination.text = it.title
        })


        viewModel.date.observe(this, Observer {
            binding.date.text = it
        })

        viewModel.time.observe(this, Observer {
            binding.time.text = it
        })

        viewModel.price.observe(this, Observer {
            if (binding.pricePerPerson.text.isNullOrEmpty())
                binding.pricePerPerson.setText("$it")

            pricePerPerson = it
            calculateTotalPrice()
        })

        viewModel.car.observe(this, Observer {
            binding.car.text = it.fullInfo
        })


        viewModel.seats.observe(this, Observer {
            binding.seatsPickButton.currentNum = it
            seatsNum = it
            calculateTotalPrice()
            if (it < 2) {
                binding.seats.text = "$it ${getString(R.string.seat)}"
            } else {
                binding.seats.text = "$it ${getString(R.string.seats)}"
            }
        })


        viewModel.bags.observe(this, Observer {
            binding.bags.text = getString(it.textInt)
        })

        viewModel.success.observe(this, Observer {
            createToast(getString(R.string.submit_trip_offer_success))
            val resultIntent = Intent()
            resultIntent.putExtra("trip", it)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        })

        viewModel.pet.observe(this, Observer {
            if (it != binding.petsSwitch.isChecked)
                binding.petsSwitch.isChecked = it
            if (it) {
                binding.pet.text = getString(R.string.allowed)
            } else {
                binding.pet.text = getString(R.string.not_allowed)
            }
        })


    }


    @SuppressLint("SetTextI18n")
    override fun setUpViews() {

        viewModel.loadUserCars()
        viewModel.setSeats(1)

        viewModel.setDestinationFrom(destinationFrom)
        viewModel.setDestinationTo(destinationTo)

        binding.description.filters = arrayOf<InputFilter>(LengthFilter(descriptionMaxLength))
        binding.characterCount.text = "0/$descriptionMaxLength"


        binding.seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
            override fun onPickAction(value: Int) {
                viewModel.setSeats(value)
            }
        }

        binding.pricePerPerson.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                var num = 0f
                try {
                    val text = editable ?: num
                    num = text.toString().toFloat()
                } catch (e: Exception) {

                }
                viewModel.setPrice(num)
            }

        })



        binding.description.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val length = editable?.length ?: 0
                binding.characterCount.text = "$length/$descriptionMaxLength"
            }

        })


        binding.petsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPet(isChecked)
        }

        binding.back.button.setOnClickListener {
            finish()
        }


        binding.ediPickUp.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 1, bundleOf("autocompleteType" to ADDRESS))
        }

        binding.pickBags.setOnClickListener {
            bagsStatusPickBottomSheetDialog = BagsStatusPickBottomSheetDialog(this@NewTripActivity::onBagsItemClickListener)
            bagsStatusPickBottomSheetDialog.show(supportFragmentManager, "bagsStatusPickBottomSheetDialog")
        }


        binding.editDate.setOnClickListener {
            dateDialog = DatePickerCustomDialog(binding.date.text, viewModel::applyDate)
            dateDialog.show(supportFragmentManager, "dateDialog")

        }

        binding.editTime.setOnClickListener {
            timeDialog = TimePickerCustomDialog(binding.time.text, viewModel::applyTime)
            timeDialog.show(supportFragmentManager, "dateDialog")

        }


        binding.pickCar.setOnClickListener {
            carPickBottomSheetDialog = CarPickBottomSheetDialog(carList, this@NewTripActivity::onCarItemClickListener)
            carPickBottomSheetDialog.show(supportFragmentManager, "carPickBottomSheetDialog")
        }


        with(binding) {
            save.setOnClickListener {
                if (pickUpDestination.isCorrect() and date.isCorrect() and time.isCorrect() and car.isCorrect() and bags.isCorrect() and pet.isCorrect()) {
                    if (validateDateTimeDiffer(date.text.toString(), time.text.toString(), resources.getInteger(R.integer.Time_difference))) {
                        viewModel.registerTrip(localUser, description.text.toString())
                    } else {
                        createToast(getString(R.string.ERROR_TRIP_TIMESTAMP))
                    }
                }
            }
        }
    }

    private fun onBagsItemClickListener(bagsStatusType: BagsStatusType) {
        viewModel.setBags(bagsStatusType)
    }


    private fun onCarItemClickListener(car: Car) {
        viewModel.setCar(car)
        carPickBottomSheetDialog.dismiss()
    }


    private fun calculateTotalPrice() {
        binding.totalPrice.setSpanTextColor(
            Pair("$seatsNum x ${pricePerPerson} ${getString(R.string.euro_symbol)}\n${getString(R.string.sum)} ", R.color.white_60_new),
            Pair("${pricePerPerson * seatsNum} ${getString(R.string.euro_symbol)}", R.color.white)
        )
    }

//    @SuppressLint("RestrictedApi")
//    override fun onBackPressed() {
//
//        if (viewModel.destinationFrom.value?.placeId != null && viewModel.destinationTo.value?.placeId != null) {
//            if (nav.backStack.size == 2) {
//                openDialog()
//            } else if (nav.currentDestination?.id != R.id.successTripFragment) {
//                super.onBackPressed()
//            }
//        } else {
//            super.onBackPressed()
//        }
//
//    }
//
//
//
//    private fun openDialog() {
//        ExitCustomDialog(this, this::exitCustomDialogAnswerType, getString(R.string.prompt_cancel_offer), 1).show(supportFragmentManager, "exitCustomDialog")
//    }
//
//    private fun exitCustomDialogAnswerType(result: AnswerType) {
//        if (result == AnswerType.Yes)
//            finish()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                viewModel.setDestinationPickUp(id, title)
            }
        }
    }

}