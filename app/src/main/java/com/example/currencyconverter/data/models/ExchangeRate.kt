package com.example.currencyconverter.data.models

data class ExchangeRate(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)