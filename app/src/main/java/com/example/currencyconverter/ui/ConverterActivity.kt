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
            ArrayAdapter.createFromResource(
                this,
                R.array.symbols,
                android.R.layout.simple_spinner_item
            )

        with(binding) {
            spFromValue.adapter = symbolsAdapter
            spToValue.adapter = symbolsAdapter

        binding.spFromValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromCurrency = adapterView?.getItemAtPosition(position).toString()
                viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
                getResult(binding.etAmount.text.toString(), exchangeRate)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

            spToValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    toCurrency = adapterView?.getItemAtPosition(position).toString()
                    viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            etAmount.doOnTextChanged { _, _, _, _ ->
                getResult(exchangeRate)
            }

            ivReverse.setOnClickListener {
                val spinnerPositionFrom = symbolsAdapter.getPosition(fromCurrency)
                val spinnerPositionTo = symbolsAdapter.getPosition(toCurrency)

                spFromValue.setSelection(spinnerPositionTo)
                spToValue.setSelection(spinnerPositionFrom)

                val buff = fromCurrency
                fromCurrency = toCurrency
                toCurrency = buff

                viewModel.getExchangeRate(API_KEY, toCurrency, fromCurrency)
            }

            viewModel.exchangeRateVM.observe(this@ConverterActivity, { response ->
                when (response) {
                    is Resourse.Success -> {
                        pbResult.isVisible = false
                        response.data.let {
                            if (it != null) {
                                exchangeRate = it.result
                                getResult(exchangeRate)
                            }
                        }
                    }
                    is Resourse.Error -> {
                        pbResult.isVisible = false
                        response.message?.let {
                            Log.e("TAG", "An error occurred: $it")
                        }
                    }
                    is Resourse.Loading -> {
                        pbResult.isVisible = true
                    }
                }
            })
        }
    }

    private fun getResult(er: Double) {
        with(binding) {
            if (etAmount.text.toString() != "") {
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