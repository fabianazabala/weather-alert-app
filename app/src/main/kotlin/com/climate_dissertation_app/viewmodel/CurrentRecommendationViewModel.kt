package com.climate_dissertation_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentRecommendationViewModel : ViewModel() {

    val recommendedClothes = MutableLiveData<ClothItem>()

}