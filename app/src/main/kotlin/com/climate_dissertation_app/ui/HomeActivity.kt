package com.climate_dissertation_app.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.TemperatureService
import com.climate_dissertation_app.ui.HomeActivity.Companion.weatherDataKey
import com.climate_dissertation_app.ui.HomeActivity.Companion.weatherMessages
import com.climate_dissertation_app.viewmodel.WeatherDetails
import com.climate_dissertation_app.viewmodel.WeatherRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import java.io.IOException
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

    companion object {
        const val weatherMessages = 1
        const val weatherDataKey = "weather"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mainScreenTitle = findViewById<TextView>(R.id.mainScreenTitle)
        requestLocationPermission()
        displayCurrentLocation(RefreshTitleHandler(Looper.getMainLooper(), mainScreenTitle))
    }


    @SuppressLint("MissingPermission")
    private fun displayCurrentLocation(handler: RefreshTitleHandler) {
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationProvider.lastLocation.addOnSuccessListener { location ->
            val callback =
                GetWeatherCallback(handler, temperatureService, objectMapper)
            weatherRepository.fetchWeatherResponse(location.latitude, location.longitude, callback)
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

class GetWeatherCallback(
    private val handler: RefreshTitleHandler,
    private val temperatureService: TemperatureService,
    private val objectMapper: ObjectMapper
) : Callback {

    override fun onFailure(call: Call, e: IOException) {
        TODO("Not yet implemented")
    }

    override fun onResponse(call: Call, response: Response) {
        val bodyString = response.body()?.string() ?: throw RuntimeException("No body")

        val weather = objectMapper.readValue(bodyString, WeatherDetails::class.java)
            .let { weatherDetails ->
                weatherDetails.copy(
                    weatherData = temperatureService.convertToCelsius(
                        weatherDetails.weatherData
                    )
                )
            }
        Bundle()
            .also { bundle -> bundle.putSerializable(weatherDataKey, weather) }
            .let { bundle ->
                Message.obtain().also { message ->
                    message.what = weatherMessages
                    message.data = bundle
                }
            }.run { handler.sendMessage(this) }
    }
}

class RefreshTitleHandler(looper: Looper, private val mainScreenTitle: TextView) : Handler(looper) {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (msg.what == weatherMessages) {
            val weatherDetails = msg.data[weatherDataKey] as WeatherDetails
            mainScreenTitle.text = weatherDetails.cityName
        }
    }
}