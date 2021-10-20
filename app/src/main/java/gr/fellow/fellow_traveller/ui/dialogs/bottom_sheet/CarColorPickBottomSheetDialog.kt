package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.CarColorPickBottomSheetDialogBinding
import gr.fellow.fellow_traveller.domain.car.CarColor
import gr.fellow.fellow_traveller.ui.car.CarColorPickAdapter


class CarColorPickBottomSheetDialog(
    private val colorList: MutableList<CarColor>,
    private val onItemClickListener: (CarColor) -> Unit,
) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CarColorPickBottomSheetDialogBinding = CarColorPickBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)

        val carAdapter = CarColorPickAdapter(onItemClickListener)
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, 5)
        binding.recyclerView.adapter = carAdapter
        carAdapter.submitList(colorList)
        return binding.root.rootView
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }


}