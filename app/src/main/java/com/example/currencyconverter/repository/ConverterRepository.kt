package com.example.currencyconverter.repository

import com.example.currencyconverter.data.ConverterApi
import javax.inject.Inject

class ConverterRepository @Inject constructor(private val api:ConverterApi) {

    suspend fun getExchangeRate(apiKey:String, to:String, from: String) =
        api.getExchangeRate(apiKey,to, from, 1)
}