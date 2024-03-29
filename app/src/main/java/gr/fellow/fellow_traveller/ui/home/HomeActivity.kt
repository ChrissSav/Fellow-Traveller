package gr.fellow.fellow_traveller.ui.home

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityHomeBinding
import gr.fellow.fellow_traveller.service.NotificationJobService
import gr.fellow.fellow_traveller.service.NotificationSocketViewModel
import gr.fellow.fellow_traveller.ui.extensions.bottomNav
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import gr.fellow.fellow_traveller.ui.main.MainActivity
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


@AndroidEntryPoint
class HomeActivity : BaseActivityViewModel<ActivityHomeBinding, HomeViewModel>(HomeViewModel::class.java), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var reference: DatabaseReference
    private lateinit var conversationListener: ValueEventListener

    @Inject
    lateinit var viewModelSecond: NotificationSocketViewModel

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    private var bottomNavButtons = mutableListOf<Pair<ImageView, Int>>()

    private val homeLayout = listOf(
        R.id.destination_main,
        R.id.destination_trips,
        R.id.destination_messenger
    )

    override fun provideViewBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.user.observe(this, Observer {
            loadConversations(it.id)
        })


        viewModel.reloadConnection.observe(this, Observer {
            if (it) {
                /* viewModel.loadCars()
                    viewModel.loadTripsAsCreator()
                    viewModel.loadTripsAsPassenger()
                    viewModel.loadTripsAsCreatorHistory()
                    viewModel.loadTripsAsPassengerHistory()
                    viewModel.loadNotifications()
                    viewModel.loadReviews()
                    viewModel.reload(false)*/
            }
        })



        viewModel.load.observe(this, Observer {
            if (it) {
                hideKeyboard()
                binding.genericLoader.root.visibility = View.VISIBLE
            } else
                binding.genericLoader.root.visibility = View.GONE
        })

        viewModel.error.observe(this, Observer {
            if (it.internal) {
                createAlerter(getString(it.messageId))
            } else
                createAlerter(it.message)
        })

        viewModel.logout.observe(this, Observer {
            if (it) {
                cancelJob()
                startActivityClearStack(MainActivity::class)
            }
        })



        viewModelSecond.notificationCount.observe(this, Observer {
//            if (it > 0)
//                viewModel.loadNotifications(true)
//            setUpNotifications(it)
        })

    }

    private fun initialBottomNavButtonsList() {
        bottomNavButtons = mutableListOf(
            Pair(binding.home, R.id.destination_main),
            Pair(binding.trips, R.id.destination_trips),
            Pair(binding.messenger, R.id.destination_messenger)
        )
    }


    override fun setUpViews() {
        viewModel.loadUserInfo()
        viewModel.loadCars()
        /* viewModel.loadTripsAsCreator()
         viewModel.loadTripsAsPassenger()
         viewModel.loadTripsAsCreatorHistory()
         viewModel.loadTripsAsPassengerHistory()*/
//        viewModel.loadNotifications()
//        viewModel.loadReviews()

        scheduleJob()
        initialBottomNavButtonsList()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)


        navController.addOnDestinationChangedListener { _, destination, _ ->

            setupBottomNavMenu(destination.id)


            showHideBottomNav(homeLayout.contains(destination.id))
            showHideButtons(destination.id == R.id.destination_main)


            if (destination.id == R.id.destination_notifications)
                viewModelSecond.updateNotificationCount(0)


        }


        binding.home.setOnClickListener(this)
        binding.trips.setOnClickListener(this)
        binding.messenger.setOnClickListener(this)
        binding.notification.setOnClickListener(this)
        binding.account.setOnClickListener(this)

        val networkCallback: NetworkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                Timer("SettingUp", false).schedule(1000) {
                    // viewModel.reload(true)
                }

            }

            override fun onLost(network: Network) {
                // network unavailable
            }
        }

        val connectivityManager: ConnectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }


    private fun setupBottomNavMenu(destination: Int) {
        bottomNavButtons.forEach {
            if (destination in homeLayout)
                if (it.second != destination)
                    setButtonUnCheck(it.first)
                else
                    setButtonCheck(it.first)
        }
    }

    private fun setButtonUnCheck(imageView: ImageView) {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.gray_bottom), android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun setButtonCheck(imageView: ImageView) {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun showHideBottomNav(targetHeight: Int) {
        val slideAnimator = ValueAnimator
            .ofInt(binding.bottomNavigationView.layoutParams.height, targetHeight)
            .setDuration(200)
        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Int
            binding.bottomNavigationView.layoutParams.height = value
            binding.bottomNavigationView.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.start()
    }


    private fun showHideButtons(show: Boolean = false) {
        with(binding) {
            if (show) {
                account.visibility = View.VISIBLE
                notification.visibility = View.VISIBLE
            } else {
                account.visibility = View.GONE
                notification.visibility = View.GONE
            }

        }
    }

    private fun showHideBottomNav(show: Boolean = false) {
        with(binding) {
            if (show) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                bottomNavigationView.visibility = View.GONE
            }

        }
    }


    private fun setUpNotifications(num: Int) {
        /* if (num < 1) {
             binding.notificationCount.visibility = View.INVISIBLE
         } else {
             binding.notificationCount.text = num.toString()
             binding.notificationCount.visibility = View.VISIBLE
         }*/

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.home -> {
                navController.bottomNav(R.id.destination_main)
            }
            R.id.trips -> {
                navController.bottomNav(R.id.destination_trips)
            }
            R.id.messenger -> {
                navController.bottomNav(R.id.destination_messenger)
            }
            R.id.notification -> {
                navController.bottomNav(R.id.destination_notifications)
            }
            R.id.account -> {
                navController.bottomNav(R.id.destination_info)
            }
        }

    }

    private fun loadConversations(userId: String) {
        reference = firebaseDatabase.getReference("UserTrips").child(userId)

        conversationListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                val tempList = mutableListOf<Conversation>()

                dataSnapshot.children.forEach {
                    it.getValue(Conversation::class.java)?.let { conversation ->
                        tempList.add(conversation)
                    }
                }

                //  viewModel.loadConversations(tempList)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i(NotificationJobService.TAG, "loadConversation:onCancelled", databaseError.toException())
                // ...
            }
        }
        reference.addValueEventListener(conversationListener)
    }


    override fun onDestroy() {
        reference.removeEventListener(conversationListener)
        super.onDestroy()
    }


    fun readAllUnReadNotification() {
        // viewModel.readAllUnReadNotification()
    }
}