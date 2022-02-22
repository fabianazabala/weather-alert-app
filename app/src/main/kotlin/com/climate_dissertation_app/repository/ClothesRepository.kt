package com.climate_dissertation_app.repository

import com.climate_dissertation_app.R
import com.climate_dissertation_app.viewmodel.ClothItem
import com.climate_dissertation_app.viewmodel.ClothPosition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClothesRepository @Inject constructor() {

    fun coldDay(): List<ClothItem> = listOf(
        Hats.winterHat,
        Top.sweaterWoman,
        Top.winterJacket,
        Pants.tights,
        Pants.skirt,
        Shoes.boots,
        Accessories.scarf,
        Accessories.gloves
    )

    fun coldAndWindyDay(): List<ClothItem> = listOf(
        Hats.winterHat,
        Top.sweaterWoman,
        Top.winterJacket,
        Pants.trousers,
        Shoes.winterBootWoman,
        Accessories.scarf,
        Accessories.gloves
    )

    fun freshDay(): List<ClothItem> = listOf(
        Top.sweaterWoman,
        Top.jacket,
        Pants.jeans,
        Shoes.sneakers,
    )

    fun sportsDay() = listOf(
        Top.sportClothesWoman,
        Top.sportJacket,
        Shoes.runningShoes,
        Hats.cap
    )

    fun warmDay() = listOf(
        Hats.hat,
        Top.blouse,
        Pants.summerSkirt,
        Shoes.sandals
    )

    fun beachDay() = listOf(
        Top.swimmingSuitWomen,
        Shoes.sandals,
        Accessories.sunscreen,
        Accessories.ball
    )

    fun rainyDay() = listOf(
        Top.rainCoat,
        Pants.trousers,
        Shoes.rainBoots
    )

    fun snowyDay() = listOf(
        Top.winterJacket,
        Pants.winterPants,
        Shoes.boots,
        Accessories.gloves,
        Accessories.scarf
    )
}

class Hats {
    companion object {
        val winterHat = ClothItem(R.drawable.winter_hat, "Winter Hat", ClothPosition.HEAD)
        val hat = ClothItem(R.drawable.hat, "Hat", ClothPosition.HEAD)
        val cap = ClothItem(R.drawable.cap, "Cap", ClothPosition.HEAD)
    }
}

class Top {
    companion object {
        val winterJacket = ClothItem(R.drawable.winter_jacket, "Winter Jacket", ClothPosition.TORSO)
        val jacket = ClothItem(R.drawable.jacket, "Jacket", ClothPosition.TORSO)
        val sweaterWoman = ClothItem(R.drawable.sweater_woman, "Sweater", ClothPosition.TORSO)
        val sweaterMan = ClothItem(R.drawable.sweater, "Sweater", ClothPosition.TORSO)
        val blouse = ClothItem(R.drawable.blouse, "Blouse", ClothPosition.TORSO)
        val sportClothesWoman =
            ClothItem(R.drawable.sport_clothes_woman, "Sport Clothes", ClothPosition.TORSO)
        val sportJacket = ClothItem(R.drawable.sport_jacket, "Sport Jacket", ClothPosition.TORSO)
        val swimmingSuitWomen =
            ClothItem(R.drawable.swimming_suit_women, "Swimming Suit", ClothPosition.TORSO)
        val swimmingSuitMen =
            ClothItem(R.drawable.swimming_suit_men, "Swimming Suit", ClothPosition.TORSO)
        val rainCoat = ClothItem(R.drawable.rain_coat, "Rain Coat", ClothPosition.TORSO)
    }
}

class Pants {
    companion object {
        val skirt = ClothItem(R.drawable.skirt, "Skirt", ClothPosition.LEGS)
        val summerSkirt = ClothItem(R.drawable.summer_skirt, "Summer Skirt", ClothPosition.LEGS)
        val trousers = ClothItem(R.drawable.trousers, "Trousers", ClothPosition.LEGS)
        val tights = ClothItem(R.drawable.tights, "Tights", ClothPosition.LEGS)
        val jeans = ClothItem(R.drawable.jeans, "Jeans", ClothPosition.LEGS)
        val winterPants = ClothItem(R.drawable.winter_pants, "Winter Pants", ClothPosition.LEGS)
    }
}

class Shoes {
    companion object {
        val boots = ClothItem(R.drawable.boots, "Boots", ClothPosition.FEET)
        val winterBootWoman =
            ClothItem(R.drawable.winter_boot_woman, "Winter Boots", ClothPosition.FEET)
        val sneakers = ClothItem(R.drawable.sneakers, "Sneakers", ClothPosition.FEET)
        val sandals = ClothItem(R.drawable.sandals, "Sandals", ClothPosition.FEET)
        val runningShoes = ClothItem(R.drawable.running_shoes, "Running Shoes", ClothPosition.FEET)
        val rainBoots = ClothItem(R.drawable.rain_boot, "Rain Boots", ClothPosition.FEET)
    }
}

class Accessories {
    companion object {
        val gloves = ClothItem(R.drawable.gloves, "Gloves", ClothPosition.ACCESSORY)
        val scarf = ClothItem(R.drawable.winter_scarf, "Scarf", ClothPosition.ACCESSORY)
        val sunscreen = ClothItem(R.drawable.sun_screen, "Sunscreen", ClothPosition.ACCESSORY)
        val ball = ClothItem(R.drawable.ball, "Ball", ClothPosition.ACCESSORY)
    }
}