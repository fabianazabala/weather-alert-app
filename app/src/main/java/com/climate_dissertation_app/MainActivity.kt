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
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi


import com.google.android.gms.tasks.OnCompleteListener
import java.lang.RuntimeException


class MainActivity : AppCompatActivity() {
   private lateinit var locationProvider: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        verifyLocationPermission()
        fetchLocation()
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, Home::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }

    private fun fetchLocation() {
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

    private fun verifyLocationPermission(): Boolean {
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

}