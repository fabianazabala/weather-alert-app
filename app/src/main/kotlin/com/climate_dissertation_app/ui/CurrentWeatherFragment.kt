package com.climate_dissertation_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.climate_dissertation_app.R
import com.climate_dissertation_app.viewmodel.WeatherDetails

private const val ARG_WEATHER_DETAILS = "weatherDetails"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentWeatherFragment : Fragment() {
    private var weatherDetails: WeatherDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weatherDetails = it.getSerializable(ARG_WEATHER_DETAILS) as WeatherDetails
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(weatherDetails: WeatherDetails) =
            CurrentWeatherFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_WEATHER_DETAILS, weatherDetails)
                }
            }
    }
}