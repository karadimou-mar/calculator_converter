package com.example.calculatorconverter.network

import com.example.calculatorconverter.model.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("latest")
    fun getLatest(
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String = "USD,AUD,CAD,JPY,GBP"): Call<Currency>

    @GET("{date}")
    fun getHistorical(
        @Path("date") date: String,
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String) : Call<Currency>
}