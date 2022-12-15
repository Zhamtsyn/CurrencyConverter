package com.example.currencyconverter.data

import com.example.currencyconverter.data.models.ExchangeRate
import com.example.currencyconverter.util.Constants.Companion.API_KEY
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ConverterApi {

    @GET("convert?")
    fun getExchangeRate(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("to")
        to:String,
        @Query("from")
        from:String,
        @Query("amount")
        amount:Int
    ): Observable<Response<ExchangeRate>>
}