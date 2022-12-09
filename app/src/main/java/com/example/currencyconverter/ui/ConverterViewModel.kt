package com.example.currencyconverter.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.repository.ConverterRepository
import com.example.currencyconverter.response.*
import com.example.currencyconverter.util.Resourse
import kotlinx.coroutines.launch
import retrofit2.Response

class ConverterViewModel(private val converterRepository: ConverterRepository) : ViewModel() {

    val exchangeRateVM:MutableLiveData<Resourse<ExchangeRate>> = MutableLiveData()

    fun getExchangeRate(apiKey:String, to:String, from: String){
        viewModelScope.launch {
            exchangeRateVM.postValue(Resourse.Loading())
            val response = converterRepository.getExchangeRate(apiKey, to, from)
            exchangeRateVM.postValue(handleExchangeRateResponse(response))
        }
    }

    private fun handleExchangeRateResponse(response: Response<ExchangeRate>):Resourse<ExchangeRate>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resourse.Success(resultResponse)
            }
        }
        return Resourse.Error(response.message())
    }
}