package gr.fellow.fellow_traveller.data.base

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_ACCESS_TOKEN
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_REFRESH_TOKEN
import gr.fellow.fellow_traveller.utils.set
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

abstract class BaseActivityViewModel<VB : ViewBinding, VM : BaseViewModel>(clazz: Class<VM>) : AppCompatActivity() {

    protected lateinit var binding: VB
    protected val viewModel: VM by lazy { ViewModelProvider(this).get(clazz) }

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var deleteUserAuthLocalUseCase: DeleteUserAuthLocalUseCase


    abstract fun provideViewBinding(): VB

    abstract fun setUpObservers()

    abstract fun setUpViews()

    open fun handleIntent() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = provideViewBinding()
        setContentView(binding.root)


        handleIntent()
        setUpViews()
        setUpObservers()

        viewModel.forceLogOut.observe(this, Observer {
            if (it) {
                runBlocking {
                    sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = null
                    sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = null
                    deleteUserAuthLocalUseCase()
                    Log.i("rpjgpoirjgre", "register_fab")
                    startActivityClearStack(MainActivity::class)
                }
            }

        })
    }


}