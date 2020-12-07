package gr.fellow.fellow_traveller.data.base

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import gr.fellow.fellow_traveller.service.NotificationJobService
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.utils.NOTIFICATION_SERVICE_CODE
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_ACCESS_TOKEN
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_REFRESH_TOKEN
import gr.fellow.fellow_traveller.utils.set
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


abstract class BaseActivityViewModel<VB : ViewBinding, VM : BaseViewModel>(clazz: Class<VM>) : AppCompatActivity() {
    private val TAG = "NotificationJobService"
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
                    cancelJob()
                    sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = null
                    sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = null
                    deleteUserAuthLocalUseCase()
                    Log.i("rpjgpoirjgre", "register_fab")
                    startActivityClearStack(MainActivity::class)
                }
            }

        })
    }

    open fun scheduleJob() {
        if (isJobServiceOn())
            cancelJob()
        val componentName = ComponentName(this, NotificationJobService::class.java)
        val info = JobInfo.Builder(NOTIFICATION_SERVICE_CODE, componentName)
            .setPersisted(true)
            .setPeriodic((15 * 60 * 1000).toLong())
            .build()
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }
    }

    open fun cancelJob() {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(NOTIFICATION_SERVICE_CODE)
        Log.d(TAG, "Job cancelled")
    }

    private fun isJobServiceOn(): Boolean {
        val scheduler = this.getSystemService(JOB_SCHEDULER_SERVICE) as (JobScheduler)
        scheduler.allPendingJobs.forEach {
            if (it.id == NOTIFICATION_SERVICE_CODE) {
                Log.d(TAG, "isJobServiceOn")
                return@isJobServiceOn true
            }
        }
        return false
    }

}