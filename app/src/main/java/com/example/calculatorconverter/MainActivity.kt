package com.example.calculatorconverter


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var hasError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun onNumberClicked(view: View) {
        if (hasError) {
            textView.text = (view as Button).text
            Log.d("XCALCULATOR", "bUTTON PRESSED")
            hasError = false
        } else {
            textView.append((view as Button).text)
            Log.d("YCALCULATOR", "bUTTON PRESSED")
        }
        //handling error
    }

}