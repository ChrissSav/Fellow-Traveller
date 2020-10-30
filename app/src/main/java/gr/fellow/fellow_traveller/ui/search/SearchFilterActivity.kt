package gr.fellow.fellow_traveller.ui.search

import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySearchFilterBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.BagsStatusPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.PetBottomSheetDialog


class SearchFilterActivity : BaseActivity<ActivitySearchFilterBinding>() {


    private lateinit var petBottomSheetDialog: PetBottomSheetDialog
    private lateinit var bagsStatusPickBottomSheetDialog: BagsStatusPickBottomSheetDialog

    override fun provideViewBinding(): ActivitySearchFilterBinding =
        ActivitySearchFilterBinding.inflate(layoutInflater)


    override fun handleIntent() {
    }

    override fun setUpObservers() {

    }

    override fun setUpViews() {

        with(binding) {


            pet.setOnClickListener {
                petBottomSheetDialog = PetBottomSheetDialog(this@SearchFilterActivity::onPetItemClickListener)
                petBottomSheetDialog.show(supportFragmentManager, "petBottomSheetDialog")

            }

            backButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun onPetItemClickListener(petAnswerType: PetAnswerType) {

    }
}