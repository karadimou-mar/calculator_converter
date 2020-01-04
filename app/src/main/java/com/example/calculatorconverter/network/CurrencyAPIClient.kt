package com.example.calculatorconverter.network

import com.example.calculatorconverter.BuildConfig
import com.example.calculatorconverter.model.Currency
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyAPIClient {

    val BASE_URL = "http://data.fixer.io/api/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)

    fun getLatest(): Call<Currency> {
        return api.getLatest(BuildConfig.PUBLIC_KEY,"")
    }

     fun getHistorical(date: String, symbols: String): Call<Currency>{
        return api.getHistorical(date, BuildConfig.PUBLIC_KEY,symbols)
    }
}