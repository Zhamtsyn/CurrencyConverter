package com.example.currencyconverter.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.models.ExchangeRate
import com.example.currencyconverter.util.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val converterRepository: ConverterRepository) : ViewModel() {

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