package com.example.calculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CurrencySelector : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_selector)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp)

        val clickedBy = intent.getIntExtra("clickedBy", 1)
        val list: ListView = findViewById(R.id.list)

        val arrayList: ArrayList<HashMap<String, String>> = ArrayList()
        val names =
            JsonDataModel.GetData(applicationContext.getSharedPreferences("currencyData", 0))
                .getInstance()
                .countryList

        val from = arrayOf("name")
        val to = IntArray(1)
        to[0] = R.id.text

        for (i in 0 until names!!.length() step 1) {
            val hash = HashMap<String, String>()
            hash["name"] = names[i] as String
            arrayList.add(hash)
        }

        list.adapter = SimpleAdapter(this, arrayList, R.layout.list_view_items, from, to)
        list.setOnItemClickListener { _: AdapterView<*>, view: View, _: Int, _: Long ->
            val text = view.findViewById<TextView>(R.id.text)
            Log.d("currencySelector", "Selected country = ${text.text} and clickedBy = $clickedBy")
            val returnIntent = Intent().apply {
                putExtra("clickedBy", clickedBy)
                putExtra("selectedCountry", text.text.toString() + " >")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        Log.d("currencySelector","back pressed")
        finish()
    }
}
