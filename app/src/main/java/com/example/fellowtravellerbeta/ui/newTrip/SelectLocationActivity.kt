package com.example.fellowtravellerbeta.ui.newTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class SelectLocationActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: PlaceAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var placesList: ArrayList<PredictionResponse> = ArrayList()
    private lateinit var editText: EditText
    private lateinit var buttonBack: ImageButton


    private val viewModel: NewTripViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

        buttonBack = findViewById(R.id.SelectLocationActivity_back_button_search)
        mRecyclerView = findViewById(R.id.SelectLocationActivity_RecyclerView)
        editText = findViewById(R.id.SelectLocationActivity_EditText_search_place)
        editText.requestFocus();

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);
                viewModel.getAllDestinations(s.toString())
                //  GetPlaces(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //Log.i("addTextChangedListener", "beforeTextChanged");
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //Log.i("addTextChangedListener", "onTextChanged");
            }
        })

        viewModel.destinations.observe(this, Observer {
            if (it.isNotEmpty()) {
                placesList.clear()
                placesList.addAll(it)
                mRecyclerView.adapter?.notifyDataSetChanged()
            }

        })

        buttonBack.setOnClickListener {
            finish()
        }
        initializeRecycle()
    }


    private fun initializeRecycle() {
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = PlaceAdapter(placesList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(PlaceAdapter.OnItemClickListener {
            val resultIntent = Intent()
            val where = intent.getStringExtra("destination")

            if(where.equals("from")){
                viewModel.setDestinationFrom(placesList[it].placeId, placesList[it].description)
            }else{
                viewModel.setDestinationTo(placesList[it].placeId, placesList[it].description)

            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        })

    }
}