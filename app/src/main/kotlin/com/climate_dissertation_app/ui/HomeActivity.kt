package com.climate_dissertation_app.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.TemperatureService
import com.climate_dissertation_app.viewmodel.WeatherRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.*
import okhttp3.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Inject
    lateinit var temperatureService: TemperatureService

    lateinit var locationProvider: FusedLocationProviderClient

    private val locationRequestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermission()
        displayCurrentLocation()
    }


    @SuppressLint("MissingPermission")
    private fun displayCurrentLocation() =
        locationProvider.lastLocation.addOnSuccessListener { location ->
            runBlocking {
                launch(Dispatchers.IO) {
                    val weatherDetails = weatherRepository.findWeatherDetails(
                        location.latitude,
                        location.longitude
                    )
                    runOnUiThread {
                        mainScreenTitle.text = weatherDetails.cityName
                    }
                }
            }
        }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                locationRequestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            locationRequestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    if (isLocationGranted()) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        throw IllegalStateException("We need location to work, sorry")
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    throw IllegalStateException("We need location to work, sorry")
                }
                return
            }
        }
    }


    private fun isLocationGranted(): Boolean = ActivityCompat.checkSelfPermission(
        this@HomeActivity, ACCESS_FINE_LOCATION
    ) == PERMISSION_GRANTED

}