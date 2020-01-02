package com.example.calculatorconverter.model

data class Currency(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Rates
)