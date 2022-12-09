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
import com.example.currencyconverter.response.Rates
import com.example.currencyconverter.util.Constants.Companion.API_KEY
import com.example.currencyconverter.util.Resourse
import kotlin.math.roundToInt
import kotlin.reflect.full.memberProperties

class ConverterActivity : AppCompatActivity() {
    lateinit var viewModel: ConverterViewModel
    private lateinit var binding: ActivityConverterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fromCurrency = "USD"
        var toCurrency= "RUB"
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


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spToValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                toCurrency = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        viewModel.getLatest(API_KEY, "AED,AFN,ALL,AMD,ANG,AOA,ARS,AUD,AWG,AZN,BAM,BBD,BDT,BGN,BHD,BIF,BMD,BND,BOB,BRL,BSD,BTC,BTN,BWP,BYN,BYR,BZD,CAD,CDF,CHF,CLF,CLP,CNY,COP,CRC,CUC,CUP,CVE,CZK,DJF,DKK,DOP,DZD,EGP,ERN,ETB,EUR,FJD,FKP,GBP,GEL,GGP,GHS,GIP,GMD,GNF,GTQ,GYD,HKD,HNL,HRK,HTG,HUF,IDR,ILS,IMP,INR,IQD,IRR,ISK,JEP,JMD,JOD,JPY,KES,KGS,KHR,KMF,KPW,KRW,KWD,KYD,KZT,LAK,LBP,LKR,LRD,LSL,LTL,LVL,LYD,MAD,MDL,MGA,MKD,MMK,MNT,MOP,MRO,MUR,MVR,MWK,MXN,MYR,MZN,NAD,NGN,NIO,NOK,NPR,NZD,OMR,PAB,PEN,PGK,PHP,PKR,PLN,PYG,QAR,RON,RSD,RUB,RWF,SAR,SBD,SCR,SDG,SEK,SGD,SHP,SLE,SLL,SOS,SRD,STD,SVC,SYP,SZL,THB,TJS,TMT,TND,TOP,TRY,TTD,TWD,TZS,UAH,UGX,USD,UYU,UZS,VEF,VES,VND,VUV,WST,XAF,XAG,XAU,XCD,XDR,XOF,XPF,YER,ZAR,ZMK,ZMW,ZWL", fromCurrency)
        viewModel.latestVM.observe(this, {response ->
            when(response) {
                is Resourse.Success ->{
                    binding.pbResult.isVisible = false
                    response.data.let{
                        if (it!=null){
//                            for (prop in Rates::class.java.declaredFields){
//                                if(prop.name == toCurrency){
////                                    exchangeRate = Rates::class.java.getField(prop.name).toString().toDouble()
////                                    Rates::class.memberProperties.forEach { pr->
////                                        if (pr.name == toCurrency) {
////                                            exchangeRate = pr.getter.call(Rates::class.java) as Double
////                                        }
////                                    }
//                                }
//                            }
//                            for (a in Rates::class.java.annotations){
//
//                            }

//                            with(it.rates){
//                                when(toCurrency){
//                                  "AED"-> exchangeRate = AED
//                                  "AFN"-> exchangeRate = AFN
//                                  "ALL"-> exchangeRate = ALL
//                                  "AMD"-> exchangeRate = AMD
//                                  "ANG"-> exchangeRate = ANG
//                                  "AOA"-> exchangeRate = AOA
//                                  "ARS"-> exchangeRate = ARS
//                                  "AUD"-> exchangeRate = AUD
//                                  "AWG"-> exchangeRate = AWG
//                                  "AZN"-> exchangeRate = AZN
//                                  "BAM"-> exchangeRate = BAM
//                                  "BBD"-> exchangeRate = BBD
//                                  "BDT"-> exchangeRate = BDT
//                                  "BGN"-> exchangeRate = BGN
//                                  "BHD"-> exchangeRate = BHD
//                                  "BIF"-> exchangeRate = BIF
//                                  "BMD"-> exchangeRate = BMD
//                                  "BND"-> exchangeRate = BND
//                                  "BOB"-> exchangeRate = BOB
//                                  "BRL"-> exchangeRate = BRL
//                                  "BSD"-> exchangeRate = BSD
//                                  "BTC"-> exchangeRate = BTC
//                                  "BTN"-> exchangeRate = BTN
//                                  "BWP"-> exchangeRate = BWP
//                                  "BYN"-> exchangeRate = BYN
//                                  "BYR"-> exchangeRate =  BYR
//                                  "BZD"-> exchangeRate =  BZD
//                                  "CAD"-> exchangeRate =  CAD
//                                  "CDF"-> exchangeRate =  CDF
//                                  "CHF"-> exchangeRate =  CHF
//                                  "CLF"-> exchangeRate =  CLF
//                                  "CLP"-> exchangeRate =  CLP
//                                  "CNY"-> exchangeRate =  CNY
//                                  "COP"-> exchangeRate =  COP
//                                  "CRC"-> exchangeRate =  CRC
//                                  "CUC"-> exchangeRate =  CUC
//                                  "CUP"-> exchangeRate =  CUP
//                                  "CVE"-> exchangeRate =  CVE
//                                  "CZK"-> exchangeRate =  CZK
//                                  "DJF"-> exchangeRate =  DJF
//                                  "DKK"-> exchangeRate =  DKK
//                                  "DOP"-> exchangeRate =  DOP
//                                  "DZD"-> exchangeRate =  DZD
//                                   "EGP"-> exchangeRate = EGP
//                                   "ERN"-> exchangeRate = ERN
//                                   "ETB"-> exchangeRate = ETB
//                                   "EUR"-> exchangeRate = EUR.toDouble()
//                                   "FJD"-> exchangeRate = FJD
//                                   "FKP"-> exchangeRate = FKP
//                                   "GBP"-> exchangeRate = GBP
//                                   "GEL"-> exchangeRate = GEL
//                                   "GGP"-> exchangeRate = GGP
//                                   "GHS"-> exchangeRate = GHS
//                                   "GIP"-> exchangeRate = GIP
//                                   "GMD"-> exchangeRate = GMD
//                                   "GNF"-> exchangeRate = GNF
//                                   "GTQ"-> exchangeRate = GTQ
//                                   "GYD"-> exchangeRate = GYD
//                                   "HKD"-> exchangeRate = HKD
//                                   "HNL"-> exchangeRate = HNL
//                                   "HRK"-> exchangeRate = HRK
//                                   "HTG"-> exchangeRate = HTG
//                                   "HUF"-> exchangeRate = HUF
//                                   "IDR"-> exchangeRate = IDR
//                                   "ILS"-> exchangeRate = ILS
//                                   "IMP"-> exchangeRate = IMP
//                                   "INR"-> exchangeRate = INR
//                                   "IQD"-> exchangeRate = IQD
//                                   "IRR"-> exchangeRate = IRR
//                                   "ISK"-> exchangeRate = ISK
//                                   "JEP"-> exchangeRate = JEP
//                                   "JMD"-> exchangeRate = JMD
//                                   "JOD"-> exchangeRate = JOD
//                                   "JPY"-> exchangeRate = JPY
//                                   "KES"-> exchangeRate = KES
//                                   "KGS"-> exchangeRate = KGS
//                                   "KHR"-> exchangeRate = KHR
//                                   "KMF"-> exchangeRate = KMF
//                                   "KPW"-> exchangeRate = KPW
//                                   "KRW"-> exchangeRate = KRW
//                                   "KWD"-> exchangeRate = KWD
//                                   "KYD"-> exchangeRate = KYD
//                                   "KZT"-> exchangeRate = KZT
//                                   "LAK"-> exchangeRate = LAK
//                                   "LBP"-> exchangeRate = LBP
//                                   "LKR"-> exchangeRate = LKR
//                                   "LRD"-> exchangeRate = LRD
//                                   "LSL"-> exchangeRate = LSL
//                                   "LTL"-> exchangeRate = LTL
//                                   "LVL"-> exchangeRate = LVL
//                                   "LYD"-> exchangeRate = LYD
//                                   "MAD"-> exchangeRate = MAD
//                                   "MDL"-> exchangeRate = MDL
//                                   "MGA"-> exchangeRate = MGA
//                                   "MKD"-> exchangeRate = MKD
//                                   "MMK"-> exchangeRate = MMK
//                                   "MNT"-> exchangeRate = MNT
//                                   "MOP"-> exchangeRate = MOP
//                                   "MRO"-> exchangeRate = MRO
//                                   "MUR"-> exchangeRate = MUR
//                                   "MVR"-> exchangeRate = MVR
//                                   "MWK"-> exchangeRate = MWK
//                                   "MXN"-> exchangeRate = MXN
//                                   "MYR"-> exchangeRate = MYR
//                                   "MZN"-> exchangeRate = MZN
//                                   "NAD"-> exchangeRate = NAD
//                                   "NGN"-> exchangeRate = NGN
//                                   "NIO"-> exchangeRate = NIO
//                                   "NOK"-> exchangeRate = NOK
//                                   "NPR"-> exchangeRate = NPR
//                                   "NZD"-> exchangeRate = NZD
//                                   "OMR"-> exchangeRate = OMR
//                                   "PAB"-> exchangeRate = PAB
//                                   "PEN"-> exchangeRate = PEN
//                                   "PGK"-> exchangeRate = PGK
//                                   "PHP"-> exchangeRate = PHP
//                                   "PKR"-> exchangeRate = PKR
//                                   "PLN"-> exchangeRate = PLN
//                                   "PYG"-> exchangeRate = PYG
//                                   "QAR"-> exchangeRate = QAR
//                                   "RON"-> exchangeRate = RON
//                                   "RSD"-> exchangeRate = RSD
//                                   "RUB"-> exchangeRate = RUB
//                                   "RWF"-> exchangeRate = RWF
//                                   "SAR"-> exchangeRate = SAR
//                                   "SBD"-> exchangeRate = SBD
//                                   "SCR"-> exchangeRate = SCR
//                                   "SDG"-> exchangeRate = SDG
//                                   "SEK"-> exchangeRate = SEK
//                                   "SGD"-> exchangeRate = SGD
//                                   "SHP"-> exchangeRate = SHP
//                                   "SLE"-> exchangeRate = SLE
//                                   "SLL"-> exchangeRate = SLL
//                                   "SOS"-> exchangeRate = SOS
//                                   "SRD"-> exchangeRate = SRD
//                                   "STD"-> exchangeRate = STD
//                                   "SVC"-> exchangeRate = SVC
//                                   "SYP"-> exchangeRate = SYP
//                                   "SZL"-> exchangeRate = SZL
//                                   "THB"-> exchangeRate = THB
//                                   "TJS"-> exchangeRate = TJS
//                                   "TMT"-> exchangeRate = TMT
//                                   "TND"-> exchangeRate = TND
//                                   "TOP"-> exchangeRate = TOP
//                                   "TRY"-> exchangeRate = TRY
//                                   "TTD"-> exchangeRate = TTD
//                                   "TWD"-> exchangeRate = TWD
//                                   "TZS"-> exchangeRate = TZS
//                                   "UAH"-> exchangeRate = UAH
//                                   "UGX"-> exchangeRate = UGX
//                                   "USD"-> exchangeRate = USD
//                                   "UYU"-> exchangeRate = UYU
//                                   "UZS"-> exchangeRate = UZS
//                                   "VEF"-> exchangeRate = VEF
//                                   "VES"-> exchangeRate = VES
//                                   "VND"-> exchangeRate = VND
//                                   "VUV"-> exchangeRate = VUV
//                                   "WST"-> exchangeRate = WST
//                                   "XAF"-> exchangeRate = XAF
//                                   "XAG"-> exchangeRate = XAG
//                                   "XAU"-> exchangeRate = XAU
//                                   "XCD"-> exchangeRate = XCD
//                                   "XDR"-> exchangeRate = XDR
//                                   "XOF"-> exchangeRate = XOF
//                                   "XPF"-> exchangeRate = XPF
//                                   "YER"-> exchangeRate = YER
//                                   "ZAR"-> exchangeRate = ZAR
//                                   "ZMK"-> exchangeRate = ZMK
//                                   "ZMW"-> exchangeRate = ZMW
//                                   "ZWL"-> exchangeRate = ZWL
//                                }
//                            }

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
                if (etAmount.text.toString()!="" && text!=null && text.last()!='.' && text.last()!=','){
                    val amount = etAmount.text.toString().toDouble()
                    var result = amount * exchangeRate
                    result = (result * 100.0).roundToInt() / 100.0
                    tvResult.text = result.toString()
                }
                else if (etAmount.text.toString() ==""){
                    tvResult.text = ""
                }
            }
        }

        binding.ivReverse.setOnClickListener {

        }


    }
}