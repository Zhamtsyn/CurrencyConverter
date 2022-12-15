package com.example.currencyconverter.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.models.ExchangeRate
import com.example.currencyconverter.util.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val converterRepository: ConverterRepository) :
    ViewModel() {

    val exchangeRateVM: MutableLiveData<Resourse<ExchangeRate>> = MutableLiveData()

    var disposables = CompositeDisposable()

    fun getExchangeRate(apiKey: String, to: String, from: String) {
        exchangeRateVM.postValue(Resourse.Loading())
        disposables.add(
            converterRepository.getExchangeRate(apiKey, to, from)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe { response: Response<ExchangeRate> ->
                    if (response.isSuccessful) {
                        response.body()?.let { result ->
                            exchangeRateVM.postValue(Resourse.Success(result))
                        }
                    } else {
                        exchangeRateVM.postValue(Resourse.Error(response.message()))
                    }
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}