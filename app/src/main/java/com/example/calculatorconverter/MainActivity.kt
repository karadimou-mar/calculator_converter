package com.example.calculatorconverter


import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    var hasError: Boolean = false
    var lastNumeric: Boolean = false // wether the last input is a number
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

    fun onNumberClicked(view: View) {
        //textView.text = ""
        if (hasError) {
            textView?.text = (view as Button).text
            Log.d("XCALCULATOR", "bUTTON PRESSED")
            hasError = false
        } else {
            textView.append((view as Button).text)
            Log.d("YCALCULATOR", "bUTTON PRESSED")
        }
        lastNumeric = true

    }

    fun onDecimalClicked(view: View) {
        if ((!(textView?.text.toString()).contains(".", true)) && lastNumeric) {
            textView.append((view as Button).text)
            lastNumeric = true
            //TODO: operator cant be inserted when decimal point is already in
        }

    }

    fun onClearAllClicked(view: View) {
        textView?.text = "0"
        hasError = false
        lastNumeric = false
    }

    fun onClearLastClicked(view: View) {
        val string: String = textView?.text.toString()
        textView?.text = string.dropLast(1)
    }

    fun onOperatorClicked(view: View) {
        if (lastNumeric && !(textView?.text.toString()).contains(".", true)) {
            textView.append((view as Button).text)
            lastNumeric = false

        }
    }

}