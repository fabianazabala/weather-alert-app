package com.climate_dissertation_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.CurrentRecommendation
import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*
import javax.inject.Inject

const val currentRecommendationKey = "currentRecommendation"

@AndroidEntryPoint
class CurrentWeatherFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var objectMapper: ObjectMapper

    private var currentRecommendation: CurrentRecommendation? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentRecommendation = arguments?.getString(currentRecommendationKey)
            .let { objectMapper.readValue(it, CurrentRecommendation::class.java) }
        updateFragment()
    }

    private fun updateFragment() {
        updateRecommendedGreetings()
        updateRecommendedWeatherIcon()
        updateRecommendedWeatherText()

    }

    private fun updateRecommendedWeatherIcon() = recommended_weather_icon.also { icon ->
        currentRecommendation?.weatherIconResourceId?.let { iconId ->
            val picasso = Picasso.get()
            picasso.isLoggingEnabled = true
            picasso
                .load(iconId)
                .into(icon)
        }
    }

    private fun updateRecommendedWeatherText() = recommended_weather_text_view.also { textView ->
        textView.text = currentRecommendation?.recommendedWeatherText
    }

    private fun updateRecommendedGreetings() = greetings_text_view.also { textView ->
        textView.text = currentRecommendation?.recommendedGreetingsText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }
}