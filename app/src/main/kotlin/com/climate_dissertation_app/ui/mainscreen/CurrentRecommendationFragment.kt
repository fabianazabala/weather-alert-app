package com.climate_dissertation_app.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.climate_dissertation_app.R
import com.climate_dissertation_app.service.CurrentRecommendation
import com.climate_dissertation_app.service.RecommendationService
import com.climate_dissertation_app.viewmodel.ClothItem
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CurrentRecommendationFragment : Fragment() {

    @Inject
    lateinit var recommendationService: RecommendationService

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Inject
    lateinit var currentClothesFragment: CurrentClothesFragment

    @Inject
    lateinit var currentWeatherFragment: CurrentWeatherFragment

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

    private fun prepareInnerFragments(context: Context) {
        recommendationService.latestRecommendation(context)
            ?.let { updateFragments(it) }
            ?: let {
                recommendationService.refreshRecommendation(context) { updateFragments(it) }
            }
    }

    private fun updateFragments(currentRecommendation: CurrentRecommendation): Boolean {
        val transaction = parentFragmentManager.beginTransaction()

        transaction.replace(
            R.id.current_clothes_fragment_container,
            recommendedClothesFragment(currentRecommendation.recommendedClothes)
        )

        transaction.replace(
            R.id.current_weather_fragment_container,
            recommendedWeatherFragment(currentRecommendation)
        )
        transaction.commitAllowingStateLoss()
        return true
    }

    private fun recommendedClothesFragment(recommendedClothes: List<ClothItem>) =
        currentClothesFragment
            .apply {
                arguments = Bundle().also {
                    it.putParcelableArrayList(
                        recommendedClothesKey,
                        ArrayList(recommendedClothes)
                    )
                }
            }

    private fun recommendedWeatherFragment(currentRecommendation: CurrentRecommendation) =
        currentWeatherFragment
            .apply {
                arguments = Bundle().also {
                    it.putString(
                        currentRecommendationKey,
                        objectMapper.writeValueAsString(currentRecommendation)
                    )
                }
            }
}