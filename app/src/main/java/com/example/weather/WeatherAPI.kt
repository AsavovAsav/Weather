package com.example.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String,
    ): CurrentWeather
}