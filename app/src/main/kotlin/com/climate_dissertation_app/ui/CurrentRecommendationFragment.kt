package com.climate_dissertation_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.RecommendationService
import com.climate_dissertation_app.viewmodel.ClothItem
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_recommendation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CurrentRecommendationFragment : Fragment() {

    @Inject
    lateinit var recommendationService: RecommendationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_current_recommendation, container, false)
    }

    private fun loadingFragments() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(
            R.id.current_clothes_fragment_container,
            LoadingFragment()
        )
        transaction.replace(
            R.id.current_weather_fragment_container,
            LoadingFragment()
        )

        transaction.commit()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadingFragments()
        prepareInnerFragments(context)
    }

    @SuppressLint("MissingPermission")
    private fun prepareInnerFragments(context: Context) {
        LocationServices.getFusedLocationProviderClient(context)
            .lastLocation.addOnSuccessListener { location ->
                location?.let { recommendedClothesScreen(it) }
            }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { recommendedClothesScreen(it) }
            }
        }

        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
            LocationRequest.create().apply {
                interval = 30000
                fastestInterval = 10000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }, locationCallback, Looper.getMainLooper()
        )
    }

    private fun recommendedClothesScreen(location: Location) {
        runBlocking {
            val task = async(Dispatchers.IO) {
                recommendationService.recommendClothesBasedOnWeather(
                    location.latitude,
                    location.longitude
                )
            }
            val recommendedClothes = task.await()
            currentClothesFragment(recommendedClothes.recommendedClothes)
        }
    }

    private fun currentClothesFragment(recommendedClothes: List<ClothItem>): Boolean {
        val transaction = parentFragmentManager.beginTransaction()
        val fragment = CurrentClothesFragment()

        fragment.arguments = Bundle().also {
            it.putParcelableArrayList(
                CurrentClothesFragment.recommendedClothesKey,
                ArrayList(recommendedClothes)
            )
        }

        transaction.replace(
            current_clothes_fragment_container.id,
            fragment
        )
        transaction.commitAllowingStateLoss()
        return true
    }

}