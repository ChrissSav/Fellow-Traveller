package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.CarPickBottomSheetDialogBinding
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.car.CarPickAdapter

class CarPickBottomSheetDialog(
    private val carList: MutableList<Car>,
    private val onItemClickListener: (Car) -> Unit,
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = CarPickBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)
        val carAdapter = CarPickAdapter(onItemClickListener)
        binding.recyclerView.adapter = carAdapter
        carAdapter.submitList(carList)
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }


}