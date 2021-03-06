package com.example.calculatorconverter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    var hasError: Boolean = false
    var lastNumeric: Boolean = false
    var hasClearLast = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currency.setOnClickListener {

            val output = textView_output.text.toString()   //+"\u20AC"
            val hasOperator: Boolean = output.contains(getString(R.string.operatorsRegex).toRegex())
            if (!hasOperator) {
                val intent = Intent(this, CurrencyActivity::class.java)
                intent.putExtra("Output", output)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "This amount can't be converted. Try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun onNumberClicked(view: View) {
        if (hasError) {
            textView_output.text = (view as Button).text
            hasError = false
        } else {
            textView_output.append((view as Button).text)
        }
        lastNumeric = true

    }

    fun onDecimalClicked(view: View) {
        val list: List<String> = textView_output.text.split(getString(R.string.operatorsRegex).toRegex())
        if (!(list.last().contains(".")) && lastNumeric) {
            textView_output.append((view as Button).text)
            lastNumeric = false


        }

    }

    fun onClearAllClicked(view: View) {
        textView_output?.text = ""
        hasError = false
        lastNumeric = false
    }

    fun onClearLastClicked(view: View) {
        val text: String = textView_output?.text.toString()
        val last: Char = text.toCharArray()[text.length-2]
        textView_output?.text = text.dropLast(1)
        hasClearLast = true
        lastNumeric = last.isDigit()
    }

    fun onOperatorClicked(view: View) {
        if (lastNumeric) {
            textView_output.append((view as Button).text)
            lastNumeric = false

        }
    }

    fun onEqualClicked(view: View) {
        val input = textView_output.text.toString()
        val expression = ExpressionBuilder(input).build()
        if (lastNumeric) {
            try {
                val output = expression.evaluate()
                textView_output.text = output.toString()
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }

}
