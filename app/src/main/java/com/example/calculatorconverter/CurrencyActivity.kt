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
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Field
import kotlin.math.absoluteValue
import kotlin.text.Typography.times

class CurrencyActivity : AppCompatActivity() {

    var check: Int = 0


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

        val actionBar = supportActionBar
        actionBar!!.title = "Currency Activity"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //get data from intent
        val intent = intent
        val output = intent?.getStringExtra("Output")
        textView_amount.text = output

        var flag: Boolean = false//is decimal

        if (output!!.contains(".")) {
            flag = false
            Log.d("FLAG", "" + flag)
        } else {
            flag = true
            Log.d("FLAG", "" + flag)
        }

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
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (check == 1) {
                    Log.d("TESTING", "LALALALALALLA")
                    when (position) {
                        0 -> textView_currency.text =
                            if (flag) hashMap["AUD"]!!.times(output.toInt()).toString()
                            else hashMap["AUD"]!!.times(output.toDouble()).toString()

                        1 -> textView_currency.text =
                            if (flag) hashMap["CAD"]!!.times(output.toInt()).toString()
                            else hashMap["CAD"]!!.times(output.toDouble()).toString()

                        2 -> textView_currency.text =
                            if (flag) hashMap["CNY"]!!.times(output.toInt()).toString()
                            else hashMap["CNY"]!!.times(output.toDouble()).toString()

                        3 -> textView_currency.text =
                            if (flag) hashMap["GBP"]!!.times(output.toInt()).toString()
                            else hashMap["GBP"]!!.times(output.toDouble()).toString()

                        4 -> textView_currency.text =
                            if (flag) hashMap["JPY"]!!.times(output.toInt()).toString()
                            else hashMap["JPY"]!!.times(output.toDouble()).toString()

                        5 -> textView_currency.text =
                            if (flag) hashMap["USD"]!!.times(output.toInt()).toString()
                            else hashMap["USD"]!!.times(output.toDouble()).toString()

                    }
                }
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

                check = 1

                for (hash in hashMap) {
                    Log.d("RATES", "" + hash.value)
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}



