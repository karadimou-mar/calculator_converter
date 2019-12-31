package com.example.calculatorconverter.model

import com.google.gson.JsonObject

data class CurrencyObject(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: JsonObject
)