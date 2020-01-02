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

class CurrencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

       //getRates()

      spinner.adapter = CountriesArrayAdapter(this,
          listOf(
              Countries(R.drawable.usd, "USA"),
              Countries(R.drawable.cad, "CANADA"),
              Countries(R.drawable.gbp, "GREAT BRITAIN"),
              Countries(R.drawable.jpy, "JAPAN")
          ))

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TESTING", "LALALALALALLA")
                getRates()
            }

        }
}

    private fun getRates(){
        val call: Call<Currency> = CurrencyAPIClient.getLatest()
        call.enqueue(object: Callback<Currency>{
            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED CONNECTION", t.message)
            }

            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                val response: Currency? = response.body()
                val rates: Rates = response!!.rates
                Log.d("RESPONSE", ""+rates.USD)
                textView_currency.text = rates.USD.toString()
            }

        })
    }

}
