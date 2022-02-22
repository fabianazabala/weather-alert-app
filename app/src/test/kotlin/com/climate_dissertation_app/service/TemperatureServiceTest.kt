package com.climate_dissertation_app.service

import com.climate_dissertation_app.viewmodel.WeatherData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class TemperatureServiceTest {

    private lateinit var temperatureService: TemperatureService

    @BeforeEach
    internal fun setUp() {
        temperatureService = TemperatureService()
    }

    @ParameterizedTest
    @MethodSource("provideTemperaturesInCelsiusAndKelvin")
    fun `given temperature in kelvin, temperatureService converts to celsius`(
        celsius: Double,
        kelvin: Double
    ) {
        val inKelvin = WeatherData(
            temperature = kelvin,
            feelsLike = kelvin,
            minimumTemperature = kelvin,
            maximumTemperature = kelvin,
            0,
            0,
            temperatureSymbol = "°K"
        )

        val inCelsius = temperatureService.convertToCelsius(inKelvin)

        assertThat(inCelsius.temperatureSymbol).isEqualTo("°C")
        assertThat(inCelsius.temperature).isEqualTo(celsius)
        assertThat(inCelsius.feelsLike).isEqualTo(celsius)
        assertThat(inCelsius.minimumTemperature).isEqualTo(celsius)
        assertThat(inCelsius.maximumTemperature).isEqualTo(celsius)
    }

    companion object {
        @JvmStatic
        fun provideTemperaturesInCelsiusAndKelvin() = Stream.of(
            Arguments.of(15, 288.15),
            Arguments.of(-15, 258.15),
            Arguments.of(0, 273.15),
            Arguments.of(32.5, 305.65),
        )
    }
}