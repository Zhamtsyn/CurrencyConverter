package com.example.currencyconverter.data.models

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)