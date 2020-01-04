package com.example.calculatorconverter

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorconverter.model.Countries
import com.example.calculatorconverter.model.Currency
import com.example.calculatorconverter.model.Rates
import com.example.calculatorconverter.network.CurrencyAPIClient
import kotlinx.android.synthetic.main.activity_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.System.currentTimeMillis
import java.util.*


class CurrencyActivity : AppCompatActivity() {

    var check: Int = 0

    var history: Rates? = null

    val hashMap = hashMapOf(
        "AUD" to 0.0,
        "CAD" to 0.0,
        "CNY" to 0.0,
        "GBP" to 0.0,
        "JPY" to 0.0,
        "USD" to 0.0
    )

    val map = mapOf(0 to "AUD", 1 to "CAD", 2 to "CNY", 3 to "GBP", 4 to "JPY", 5 to "USD")

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
                parent?.selectedItemPosition

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
                        0 -> textView_currency.text = getResults(output, hashMap["AUD"])
                        1 -> textView_currency.text = getResults(output, hashMap["CAD"])
                        2 -> textView_currency.text = getResults(output, hashMap["CNY"])
                        3 -> textView_currency.text = getResults(output, hashMap["GBP"])
                        4 -> textView_currency.text = getResults(output, hashMap["JPY"])
                        5 -> textView_currency.text = getResults(output, hashMap["USD"])
                    }
                    //textView_date.visibility = View.VISIBLE
                    textView_info.visibility = View.VISIBLE
                    textView_info.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.fade_in
                        )
                    )
                }
            }

        }

    }


    private fun getResults(amount: String?, rate: Double?): String {
        return if (amount!!.contains(".")) {
            rate!!.times(amount.toDouble()).toString()
        } else {
            rate!!.times(amount.toInt()).toString()
        }
    }

    private fun getRates() {
        val call: Call<Currency> = CurrencyAPIClient.getLatest()
        call.enqueue(object : Callback<Currency> {
            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED RATES CONNECTION", t.message)
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

                val date = response.date
//                textView_date.text = getString(R.string.date, date)

                check = 1

                for (hash in hashMap) {
                    Log.d("RATES", "" + hash.value)
                }
            }

        })
    }


    private fun getHistorical(date: String, symbols: String) {

            val call: Call<Currency> = CurrencyAPIClient.getHistorical(date, symbols)
            call.enqueue(object : Callback<Currency> {
                override fun onFailure(call: Call<Currency>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("FAILED HISTORICAL CONN", t.message)
                }

                override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                    val resp: Currency? = response.body()
                    Log.d("response", "" + response)
                    history = resp!!.rates

                    Log.d("HISTORY TEST", "" + history)
                }

            })

    }


      fun onClickDataPicker(view: View) {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val chosenDatePickerDialog = "$dayOfMonth-${monthOfYear + 1}-$year"

                    val formattedDay = if (dayOfMonth%10==dayOfMonth) "0$dayOfMonth" else "$dayOfMonth"
                    val monthCorrected = monthOfYear + 1
                    val formattedMonth = if ((monthCorrected)%10==monthCorrected) "0$monthCorrected" else "$monthCorrected"
                    val formattedDate = "$year-$formattedMonth-$formattedDay"

                    val chosenCountry = map[spinner.selectedItemPosition]

                    getHistorical(formattedDate, chosenCountry!!)

                    val handler = Handler()
                    handler.postDelayed({
                        textView_passed.visibility = View.VISIBLE
                        textView_passed.text = getString(R.string.on_s_rate_was, chosenDatePickerDialog,
                            history?.getHistoricalRate(chosenCountry)
                        )

                        textView_current.visibility = View.VISIBLE
                        textView_current.text = getString(R.string.now_rate_is_s, hashMap[chosenCountry])

                        Log.d("Chosen", chosenCountry)
                        textView_info.visibility = View.GONE
                        textView_info.clearAnimation()
                    }, 1000)
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.maxDate = currentTimeMillis()
            datePickerDialog.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}



