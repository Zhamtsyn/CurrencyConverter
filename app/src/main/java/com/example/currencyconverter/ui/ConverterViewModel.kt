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

    val symbolsVM:MutableLiveData<Resourse<Symbols>> = MutableLiveData()

    val latestVM:MutableLiveData<Resourse<Latest>> = MutableLiveData()

    fun getSymbols(apiKey:String){
        viewModelScope.launch {
            symbolsVM.postValue(Resourse.Loading())
            val response = converterRepository.getSymbols(apiKey)
            symbolsVM.postValue(handleSymbolsResponse(response))
        }
    }

    fun getLatest(apiKey:String, to:String, from: String){
        viewModelScope.launch {
            latestVM.postValue(Resourse.Loading())
            val response = converterRepository.getLatest(apiKey, to, from)
            latestVM.postValue(handleLatestResponse(response))
        }
    }

    private fun handleSymbolsResponse(response: Response<Symbols>):Resourse<Symbols>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resourse.Success(resultResponse)
            }
        }
        return Resourse.Error(response.message())
    }

    private fun handleLatestResponse(response: Response<Latest>):Resourse<Latest>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resourse.Success(resultResponse)
            }
        }
        return Resourse.Error(response.message())
    }
}