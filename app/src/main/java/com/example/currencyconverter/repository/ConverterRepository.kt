package com.example.currencyconverter.repository

import com.example.currencyconverter.api.RetrofitInstance

class ConverterRepository {

    suspend fun getExchangeRate(apiKey:String, to:String, from: String) =
        RetrofitInstance.api.getExchangeRate(apiKey,to, from, 1)
}