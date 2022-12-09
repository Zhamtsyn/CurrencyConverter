package com.example.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ActivityConverterBinding
import com.example.currencyconverter.repository.ConverterRepository
import com.example.currencyconverter.util.Constants.Companion.API_KEY
import com.example.currencyconverter.util.Resourse
import kotlin.math.roundToInt

class ConverterActivity : AppCompatActivity() {
    lateinit var viewModel: ConverterViewModel
    private lateinit var binding: ActivityConverterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fromCurrency = "AED"
        var toCurrency= "AED"
        var exchangeRate = 0.0

        val converterRepository = ConverterRepository()
        val viewModelProviderFactory = ConverterViewModelProviderFactory(converterRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ConverterViewModel::class.java]

        val symbolsAdapter =
            ArrayAdapter.createFromResource(this, R.array.symbols, android.R.layout.simple_spinner_item)

        binding.spFromValue.adapter = symbolsAdapter
        binding.spToValue.adapter = symbolsAdapter

        binding.spFromValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromCurrency = adapterView?.getItemAtPosition(position).toString()
                viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
                getResult(binding.etAmount.text.toString(), exchangeRate)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spToValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                toCurrency = adapterView?.getItemAtPosition(position).toString()
                viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
                getResult(binding.etAmount.text.toString(), exchangeRate)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
        viewModel.exchangeRateVM.observe(this, {response ->
            when(response) {
                is Resourse.Success ->{
                    binding.pbResult.isVisible = false
                    response.data.let{
                        if (it!=null){
                            exchangeRate = it.result
                        }
                    }
                }
                is Resourse.Error -> {
                    binding.pbResult.isVisible = false
                    response.message?.let {
                        Log.e("TAG", "An error occurred: $it")
                    }
                }
                is Resourse.Loading -> {
                    binding.pbResult.isVisible = true
                }
            }
        })


        with(binding) {
            etAmount.doOnTextChanged { text, start, before, count ->
                getResult(text, exchangeRate)
            }
        }

        binding.ivReverse.setOnClickListener {
            val spinnerPositionFrom = symbolsAdapter.getPosition(fromCurrency)
            val spinnerPositionTo = symbolsAdapter.getPosition(toCurrency)

            binding.spFromValue.setSelection(spinnerPositionFrom)
            binding.spToValue.setSelection(spinnerPositionTo)

            val buff = fromCurrency
            fromCurrency = toCurrency
            toCurrency = buff

            viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
            getResult(binding.etAmount.text.toString(), exchangeRate)
        }


    }

    private fun getResult (text: CharSequence?, er:Double){
        with(binding){
            if (etAmount.text.toString()!="" && text!=null && text.last()!='.' && text.last()!=','){
                val amount = etAmount.text.toString().toDouble()
                var result = amount * er
                result = (result * 100.0).roundToInt() / 100.0
                tvResult.text = result.toString()
            }
            else if (etAmount.text.toString() ==""){
                tvResult.text = ""
            }
        }

    }
}