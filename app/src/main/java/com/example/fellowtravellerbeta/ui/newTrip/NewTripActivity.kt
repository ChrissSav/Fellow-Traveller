package com.example.fellowtravellerbeta.ui.newTrip

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.dialogs.ExitCustomDialog

class NewTripActivity : AppCompatActivity(), ExitCustomDialog.ExitCustomDialogListener {

    private lateinit var backButton: ImageButton
    private lateinit var exitButton: ImageButton
    private lateinit var labelSummary: TextView
    private lateinit var nav: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var exampleDialog: ExitCustomDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip)


        backButton = findViewById(R.id.imageButton_back)
        exitButton = findViewById(R.id.imageButton_exit)
        labelSummary = findViewById(R.id.NewTripActivity_textView_label)
        progressBar = findViewById(R.id.NewTripActivity_progressBar)


        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        exitButton.setOnClickListener {
            openDialog()
        }

        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addLocationsFragment -> progressBar.progress = 16
                R.id.addDateTimeFragment -> progressBar.progress = 33
                R.id.addBaseInfoFragment -> progressBar.progress = 48
                R.id.addPriceFragment -> progressBar.progress = 64
                R.id.addMessageFragment -> {
                    progressBar.progress = 80
                    labelSummary.visibility = View.INVISIBLE
                }
                R.id.tripSummaryFragment -> {
                    progressBar.progress = 96
                    labelSummary.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun openDialog() {
        exampleDialog = ExitCustomDialog(this)
        exampleDialog.show(supportFragmentManager, "example dialog")
    }

    override fun exitFrom(exit: Boolean) {
        exampleDialog.dismiss()
        if(exit)
            finish()
    }


}