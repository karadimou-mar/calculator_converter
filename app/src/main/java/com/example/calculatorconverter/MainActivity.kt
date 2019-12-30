package com.example.calculatorconverter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId){
            R.id.navigation_calculator -> {
                println("home pressed")
                replaceFragment(CalculatorFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_currency -> {
                println("map pressed")
                replaceFragment(CurrencyFragment())
                return@OnNavigationItemSelectedListener true
            }
        }

        false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(CalculatorFragment())
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}