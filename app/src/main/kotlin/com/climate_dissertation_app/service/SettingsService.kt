package com.climate_dissertation_app.service

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject
import javax.inject.Singleton


const val SETTINGS_KEY = "settings"

@Singleton
class SettingsService @Inject constructor(
    private val objectMapper: ObjectMapper
) {

    fun storeSettings(context: Context, settings: Settings) {
        val sharedPref =
            context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)
        sharedPref?.also {
            with(it.edit()) {
                putString(
                    SETTINGS_KEY,
                    objectMapper.writeValueAsString(settings)
                )
                apply()
            }
        }
    }

    fun fetchSettings(context: Context) =
        context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)
            ?.getString(SETTINGS_KEY, null)
            ?.let { objectMapper.readValue(it, Settings::class.java) }

}