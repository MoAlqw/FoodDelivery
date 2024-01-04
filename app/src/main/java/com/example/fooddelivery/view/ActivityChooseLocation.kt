package com.example.fooddelivery.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fooddelivery.R
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivityChooseLocationBinding
import com.example.fooddelivery.model.location.LocationAdd
import com.example.fooddelivery.viewmodel.ChooseLocationViewModel

class ActivityChooseLocation : AppCompatActivity() {

    private lateinit var binding: ActivityChooseLocationBinding

    private val viewModelLocation by lazy { ChooseLocationViewModel(Repositories.locationRepository) }

    private var isPrivateHouse = true

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinnerCities = binding.spinnerOfCities
        spinnerCities.setSelection(0)

        ArrayAdapter.createFromResource(
            this,
            R.array.cities_of_Kazakhstan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCities.adapter = adapter
        }

        binding.btnApartment.setOnClickListener {
            updateUIButtons()
        }

        binding.btnPrivateHouse.setOnClickListener {
            updateUIButtons()
        }

        binding.btnStartDelivery.setOnClickListener {
            addToLocationBD()
        }

        observeState()
        showToastMessage()

    }

    private fun updateUIButtons() {
        with (binding) {
            if (isPrivateHouse) {
                btnApartment.isEnabled = false
                btnPrivateHouse.isEnabled = true
                btnApartment.setBackgroundResource(R.drawable.btn_gradient)
                btnApartment.setTextColor(getColor(R.color.white))
                editTextApartmentOfUser.visibility = View.VISIBLE
                isPrivateHouse = false
                btnPrivateHouse.setBackgroundResource(R.drawable.btn_white)
                btnPrivateHouse.setTextColor(getColor(R.color.grey))
            } else {
                btnApartment.isEnabled = true
                btnPrivateHouse.isEnabled = false
                btnPrivateHouse.setBackgroundResource(R.drawable.btn_gradient)
                btnPrivateHouse.setTextColor(getColor(R.color.white))
                editTextApartmentOfUser.visibility = View.GONE
                editTextApartmentOfUser.setText("")
                isPrivateHouse = true
                btnApartment.setBackgroundResource(R.drawable.btn_white)
                btnApartment.setTextColor(getColor(R.color.grey))
            }
        }
    }

    private fun observeLogin() {
        viewModelLocation.isSuccessful.observe(this) { isSuccessful ->
            if (isSuccessful.equals(true)) {
                startActivity(
                    Intent(this, ActivityMainMenu::class.java)
                        .putExtra("id", intent.getLongExtra("id", -1))
                )
                finish()
            }
        }
    }

    private fun showToastMessage() {
        viewModelLocation.state.observe(this) { state ->
            Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeState() {
        viewModelLocation.inProgress.observe(this) { inProgress ->
            with(binding) {
                btnStartDelivery.isEnabled = !inProgress
                btnStartDelivery.visibility = if (!inProgress) View.VISIBLE else View.GONE
                progressBarAddLocation.visibility = if (inProgress) View.VISIBLE else View.GONE
            }
        }
    }

    private fun addToLocationBD() {
        with(binding) {
            val city = spinnerOfCities.selectedItem.toString()
            val street = streetOfUser.text.toString().trim()
            val isPrivate = isPrivateHouse
            val apartment = if (isPrivate) null else editTextApartmentOfUser.text.toString()
            val newLocationAdd = LocationAdd(city, street, isPrivate, apartment)
            viewModelLocation.addLocation(newLocationAdd)
        }
    }

}