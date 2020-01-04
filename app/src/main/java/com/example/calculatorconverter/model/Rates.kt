package com.example.calculatorconverter.model

data class Rates (
    val AUD: Double,
    val CAD: Double,
    val CNY: Double,
    val GBP: Double,
    val JPY: Double,
    val USD: Double
){
    fun getHistoricalRate(country: String) : Double{
        return when(country){
            "AUD" -> AUD
            "CAD" -> CAD
            "CNY" -> CNY
            "GBP" -> GBP
            "JPY" -> JPY
            "USD" -> USD
            else -> AUD
        }
    }
}