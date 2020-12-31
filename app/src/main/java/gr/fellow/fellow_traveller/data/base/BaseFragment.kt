package gr.fellow.fellow_traveller.data.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import gr.fellow.fellow_traveller.ui.dialogs.DisplayUserPictureDialog


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected var navController: NavController? = null


    abstract fun getViewBinding(): VB

    abstract fun setUpObservers()

    abstract fun setUpViews()

    open fun handleIntent() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            navController = Navigation.findNavController(view)

        } catch (e: Exception) {
        }

        setUpViews()

        setUpObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openDialogPicture(url: String?) {
        if (url != null && activity?.supportFragmentManager != null)
            DisplayUserPictureDialog(requireActivity(), url).show(activity?.supportFragmentManager!!, "DisplayUserPictureDialog")
    }


}
