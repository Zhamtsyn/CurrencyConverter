package com.example.currencyconverter.api

import com.example.currencyconverter.response.ExchangeRate
import com.example.currencyconverter.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ConverterApi {

    @GET("convert?")
    suspend fun getExchangeRate(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("to")
        to:String,
        @Query("from")
        from:String,
        @Query("amount")
        amount:Int
    ):Response<ExchangeRate>
}