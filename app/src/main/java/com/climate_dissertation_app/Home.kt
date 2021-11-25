package com.climate_dissertation_app

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import java.io.IOException
import java.lang.RuntimeException

class Home : AppCompatActivity() {
    private val API: String = "1f549bc4a06287175484cf71fc0a6ac0"

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textView = findViewById<TextView>(R.id.text)
        val uri = Uri.Builder()
            .scheme("https")
            .authority("api.openweathermap.org")
            .appendPath("data")
            .appendPath("2.5")
            .appendPath("weather")
            .appendQueryParameter("q", "Gdansk")
            .appendQueryParameter("appid", API)
            .build()

        client.newCall(
            Request.Builder()
                .get()
                .url(uri.toString())
                .build()
        ).enqueue(GetWeatherCallback())


    }


}

class GetWeatherCallback : Callback {

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)

    override fun onFailure(call: Call, e: IOException) {
        TODO("Not yet implemented")
    }

    override fun onResponse(call: Call, response: Response) {
        val bodyString = response.body()?.string() ?: throw RuntimeException("No body")

        val weather = objectMapper.readValue(bodyString, WeatherResponse::class.java)

        weather.weather[0].
    }

}