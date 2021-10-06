package gr.fellow.fellow_traveller.ui.newtrip

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.ui.dialogs.DatePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.TimePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.BagsStatusPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener
import kotlinx.android.synthetic.main.activity_new_trip.*
import kotlinx.android.synthetic.main.fragment_main.*


@AndroidEntryPoint
class NewTripActivity : BaseActivityViewModel<ActivityNewTripBinding, NewTripViewModel>(NewTripViewModel::class.java) {


    private lateinit var nav: NavController
    private var userRate: Float = 0f
    lateinit var localUser: LocalUser
    private var seatsNum = 1
    private var pricePerPerson = "0".toFloat()
    private lateinit var carPickBottomSheetDialog: CarPickBottomSheetDialog
    private lateinit var bagsStatusPickBottomSheetDialog: BagsStatusPickBottomSheetDialog
    private lateinit var dateDialog: DatePickerCustomDialog
    private lateinit var timeDialog: TimePickerCustomDialog
    private var carList = mutableListOf<Car>()


    override fun provideViewBinding(): ActivityNewTripBinding =
        ActivityNewTripBinding.inflate(layoutInflater)


    override fun handleIntent() {
        userRate = intent.getFloatExtra("userRate", 0f)
        localUser = intent.getParcelableExtra<LocalUser>("localUser")!!
    }

    override fun setUpObservers() {

        viewModel.carList.observe(this, { list ->
            if (list.size == 1)
                viewModel.setCar(list.first())
            carList.clear()
            carList.addAll(list)
        })

        viewModel.error.observe(this, {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.green)
            else
                createAlerter(it.message, R.color.blue_color)
        })

        viewModel.load.observe(this, {
            if (it)
                binding.progressLoad.root.visibility = View.VISIBLE
            else
                binding.progressLoad.root.visibility = View.GONE

        })


        viewModel.destinationPickUp.observe(this, {
            binding.pickUpDestination.text = it.title
        })


        viewModel.date.observe(this, {
            binding.date.text = it
        })

        viewModel.time.observe(this, {
            binding.time.text = it
        })

        viewModel.price.observe(this, {
            if (binding.pricePerPerson.text.isNullOrEmpty())
                binding.pricePerPerson.setText("$it")

            pricePerPerson = it
            calculateTotalPrice()
        })

        viewModel.car.observe(this, {
            binding.car.text = it.fullInfo
        })


        viewModel.seats.observe(this, {
            binding.seatsPickButton.currentNum = it
            seatsNum = it
            calculateTotalPrice()
            if (it < 2) {
                binding.seats.text = "$it ${getString(R.string.seat)}"
            } else {
                binding.seats.text = "$it ${getString(R.string.seats)}"
            }
        })


        viewModel.bags.observe(this, {
            binding.bags.text = getString(it.textInt)
        })


        viewModel.pet.observe(this, {
            if (it != binding.petsSwitch.isChecked)
                binding.petsSwitch.isChecked = it
            if (it) {
                binding.pet.text = getString(R.string.allowed)
            } else {
                binding.pet.text = getString(R.string.not_allowed)
            }
        })


    }


    override fun setUpViews() {

        viewModel.loadUserCars()
        viewModel.setSeats(1)

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
                var num = "0".toFloat()
                try {
                    val text = editable ?: "0"
                    num = text.toString().toFloat()
                } catch (e: Exception) {

                }
                viewModel.setPrice(num)
            }

        })


        binding.petsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPet(isChecked)
        }

        binding.back.button.setOnClickListener {
            finish()
        }


        binding.ediPickUp.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 1)
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
        binding.totalPrice.setSpanText(Pair("$seatsNum x ${pricePerPerson} ${getString(R.string.euro_symbol)}\n${getString(R.string.sum)} ", R.color.white_60_new),
            Pair("${pricePerPerson * seatsNum} ${getString(R.string.euro_symbol)}", R.color.white))
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {

        if (viewModel.destinationFrom.value?.placeId != null && viewModel.destinationTo.value?.placeId != null) {
            if (nav.backStack.size == 2) {
                openDialog()
            } else if (nav.currentDestination?.id != R.id.successTripFragment) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }

    }

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


    fun getUserRate() = userRate

    private fun openDialog() {
        ExitCustomDialog(this, this::exitCustomDialogAnswerType, getString(R.string.prompt_cancel_offer), 1).show(supportFragmentManager, "exitCustomDialog")
    }

    private fun exitCustomDialogAnswerType(result: AnswerType) {
        if (result == AnswerType.Yes)
            finish()
    }


}