package com.climate_dissertation_app.config

import com.climate_dissertation_app.repository.WeatherRestRepository
import com.climate_dissertation_app.viewmodel.WeatherRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module(includes = [RepositoryModule.BindsModule::class])
@InstallIn(value = [ActivityComponent::class, SingletonComponent::class])
class RepositoryModule {

    @Provides
    fun httpClient() = OkHttpClient()

    @Provides
    fun objectMapper(): ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)

    @Module
    @InstallIn(value = [ActivityComponent::class, SingletonComponent::class])
    interface BindsModule {
        @Binds
        fun weatherRepository(weatherRepository: WeatherRestRepository): WeatherRepository
    }
}