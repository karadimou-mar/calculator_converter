package com.example.calculatorconverter


import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    var hasError: Boolean = false
    var lastNumeric: Boolean = false // wether the last input is a number


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currency.setOnClickListener {

            val output = textView_output.text.toString()

            val intent = Intent(this, CurrencyActivity::class.java)
            intent.putExtra("Output", output)
            startActivity(intent)
        }

    }

    fun onNumberClicked(view: View) {
        //textView.text = ""
        if (hasError) {
            textView_output.text = (view as Button).text
            Log.d("XCALCULATOR", "bUTTON PRESSED")
            hasError = false
        } else {
            textView_output.append((view as Button).text)
            Log.d("YCALCULATOR", "bUTTON PRESSED")
        }
        lastNumeric = true

    }

    fun onDecimalClicked(view: View) {
        val list: List<String> = textView_output.text.split("[-+*/]".toRegex())
        if (!(list.last().contains(".")) && lastNumeric) {
            textView_output.append((view as Button).text)
            lastNumeric = true

        }

    }

    fun onClearAllClicked(view: View) {
        textView_output?.text = ""
        hasError = false
        lastNumeric = false
    }

    fun onClearLastClicked(view: View) {
        val string: String = textView_output?.text.toString()
        textView_output?.text = string.dropLast(1)
    }

    fun onOperatorClicked(view: View) {
        if (lastNumeric ) {
            textView_output.append((view as Button).text)
            lastNumeric = false

        }
    }

}