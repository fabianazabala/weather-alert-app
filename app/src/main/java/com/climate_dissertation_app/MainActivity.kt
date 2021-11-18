package com.climate_dissertation_app

import android.app.DownloadManager
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.DecimalFormat
import android.Manifest.permission
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi


import com.google.android.gms.tasks.OnCompleteListener
import java.lang.RuntimeException


class MainActivity : AppCompatActivity() {
    lateinit var etCity: EditText
    lateinit var etCountry: EditText
    lateinit var tvResult: TextView
    lateinit var locationProvider: FusedLocationProviderClient

    private val url = "http://api.openweathermap.org/data/2.5/weather";
    private val idApi = "434420eed5d609b9b0cc1258b9ee5b5a";
    private val decimalFormat = DecimalFormat("#.##");

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //etCity = findViewById(R.id.etCity)
        //etCountry = findViewById(R.id.etCountry)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        requestLocationPermission()
        fetchLocation()
        supportActionBar?.hide()
    }

    private fun fetchLocation() {
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {

            val temp = locationProvider.lastLocation

            temp.addOnSuccessListener {
                if (it != null) {
                    Toast.makeText(
                        applicationContext,
                        "${it.latitude} ${it.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun requestLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.ACCESS_FINE_LOCATION),
                101
            )
            return true
        }
        return false
    }


    fun getClimateCharacteristics(view: android.view.View) {
        var tempURL = "";
        val city = etCity.text.toString().trim()
        val country = etCountry.text.toString().trim()

        if (city.isEmpty()) {
            tvResult.text = "City can not be empty";
        } else {
            tempURL = if (country != "") {
                "$url?q=$city,$country&idApi=$idApi";
            } else {
                "$url?q=$city&idApi$idApi";
            }
            val stringrequest = StringRequest(Request.Method.POST, tempURL,
                {
                    @Override
                    fun onResponse(response: String) {
                    }
                }, {
                })
        }
    }
}