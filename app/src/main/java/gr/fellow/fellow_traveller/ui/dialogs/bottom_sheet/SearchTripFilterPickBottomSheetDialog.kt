package gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.SearchTripFilterBottomSheetDialogBinding


class SearchTripFilterPickBottomSheetDialog(

) : BottomSheetDialogFragment() {

    private lateinit var binding: SearchTripFilterBottomSheetDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchTripFilterBottomSheetDialogBinding.inflate(LayoutInflater.from(context), container, false)



        return binding.root.rootView
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog

    }

    /*  private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
          val bottomSheet =  binding.root
          val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet)
          val layoutParams = bottomSheet.layoutParams
          val windowHeight = getWindowHeight()
          if (layoutParams != null) {
              layoutParams.height = windowHeight
          }
          bottomSheet.layoutParams = layoutParams
          behavior.state = BottomSheetBehavior.STATE_EXPANDED
      }

      private fun getWindowHeight(): Int {
          // Calculate window height for fullscreen use
          val displayMetrics = DisplayMetrics()
          (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
          return displayMetrics.heightPixels
      }*/


}