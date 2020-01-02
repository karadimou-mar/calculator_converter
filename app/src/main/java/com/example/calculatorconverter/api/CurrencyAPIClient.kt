package com.example.calculatorconverter.api

import com.example.calculatorconverter.BuildConfig
import com.example.calculatorconverter.model.Currency
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyAPIClient {

    val BASE_URL = "http://data.fixer.io/api/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)

    fun getLatest(): Call<Currency> {
        return api.getLatest(BuildConfig.PUBLIC_KEY,"")
    }
}