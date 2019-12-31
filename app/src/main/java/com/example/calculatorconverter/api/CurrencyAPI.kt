package com.example.calculatorconverter.api

import com.example.calculatorconverter.model.CurrencyObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("latest")
    fun getLatest(
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String = "USD,AUD,CAD,PLN,MXN"): Call<CurrencyObject>
}