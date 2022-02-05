package com.climate_dissertation_app.viewmodel

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class WeatherDetailsTest {

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())

    @Test
    internal fun `given json response then WeatherResponse is parsed as expected`() {
        val response = objectMapper.readValue(
            Thread.currentThread().contextClassLoader.getResource("weatherResponse.json"),
            WeatherDetails::class.java
        )

        assertThat(response).hasNoNullFieldsOrProperties().usingRecursiveComparison()
    }
}