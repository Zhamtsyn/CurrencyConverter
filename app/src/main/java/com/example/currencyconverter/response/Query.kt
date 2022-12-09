package com.example.currencyconverter.response

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)