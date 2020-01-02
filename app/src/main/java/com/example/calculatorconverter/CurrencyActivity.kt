package com.example.calculatorconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.calculatorconverter.api.CurrencyAPIClient
import com.example.calculatorconverter.model.Countries
import com.example.calculatorconverter.model.Currency
import com.example.calculatorconverter.model.Rates
import kotlinx.android.synthetic.main.activity_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Field

class CurrencyActivity : AppCompatActivity() {

    val hashMap = hashMapOf(
        "AUD" to 0.0,
        "CAD" to 0.0,
        "CNY" to 0.0,
        "GBP" to 0.0,
        "JPY" to 0.0,
        "USD" to 0.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        getRates()

        spinner.adapter = CountriesArrayAdapter(
            this,
            listOf(

                Countries(R.drawable.aud, "AUD"),
                Countries(R.drawable.cad, "CAD"),
                Countries(R.drawable.cny, "CNY"),
                Countries(R.drawable.gbp, "GBP"),
                Countries(R.drawable.jpy, "JPY"),
                Countries(R.drawable.usd, "USD")
            )
        )



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TESTING", "LALALALALALLA")
                //getRates()
            }

        }
        //textView_currency.text = ""



    }

    private fun getRates() {
        val call: Call<Currency> = CurrencyAPIClient.getLatest()
        call.enqueue(object : Callback<Currency> {
            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED CONNECTION", t.message)
            }

            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                val response: Currency? = response.body()
                val rates: Rates = response!!.rates
                //Log.d("RESPONSE", "" + rates.USD)
                //textView_currency.text = rates.USD.toString()
                hashMap["AUD"] = rates.AUD
                hashMap["CAD"] = rates.CAD
                hashMap["CNY"] = rates.CNY
                hashMap["GBP"] = rates.GBP
                hashMap["JPY"] = rates.JPY
                hashMap["USD"] = rates.USD

                for (hash in hashMap){
                    Log.d("RATES", ""+hash.value)
                }
            }

        })
    }

}
