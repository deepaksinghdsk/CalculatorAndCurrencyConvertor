package com.example.calculator.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calculator.CurrencySelector
import com.example.calculator.JsonDataModel
import com.example.calculator.R
import org.json.JSONObject
import java.lang.Exception

/**
 * A placeholder fragment containing a simple view.
 */
class Fragment1 : Fragment() {

    private lateinit var tobeConverted: TextView
    private lateinit var converted: TextView
    private lateinit var convertFromName: TextView
    private lateinit var convertToName: TextView
    private lateinit var activeText: TextView
    private lateinit var activeBaseCountry: TextView

    companion object {
        val TAG: String = Fragment1::class.java.name
        lateinit var status: TextView
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        convertFromName = root.findViewById(R.id.convertFromName)
        convertToName = root.findViewById(R.id.convertToName)
        val convertFrom: LinearLayout = root.findViewById(R.id.convertFrom)
        val convertTo: LinearLayout = root.findViewById(R.id.convertTo)

        val c: Button = root.findViewById(R.id.C)
        val back: Button = root.findViewById(R.id.back)

        val data =
            JsonDataModel.GetData(context!!.getSharedPreferences("currencyData", 0)).getInstance()
        status = root.findViewById(R.id.status)
        try {
            status.text = "Last updated on ${data.dataModel!!.getString("date")}"
        }catch (e:Exception){
            e.printStackTrace()
        }

        tobeConverted = root.findViewById(R.id.tobeConverted)
        converted = root.findViewById(R.id.converted)
        activeText = tobeConverted
        tobeConverted.setTextColor(Color.WHITE)
        converted.setTextColor(Color.parseColor("#C9D6D4D4"))
        activeBaseCountry = convertFromName

        c.setOnClickListener {
            activeText.text = "0"
        }
        back.setOnClickListener {
            if (activeText.text.isNotEmpty()) {
                activeText.text =
                    if (activeText.text.length == 1) "0"
                    else activeText.text.toString().substring(
                        0,
                        activeText.text.toString().lastIndex
                    )
            }
        }
        Log.d(
            TAG, "base = " + activeBaseCountry.text.toString().substring(
                0,
                activeBaseCountry.text.toString().lastIndex - 1
            )
        )

        convertFrom.setOnClickListener {
            val intent = Intent(this.context, CurrencySelector::class.java).apply {
                putExtra("clickedBy", 1)
            }
            startActivityForResult(intent, 101)
        }
        convertTo.setOnClickListener {
            val intent = Intent(this.context, CurrencySelector::class.java).apply {
                putExtra("clickedBy", 2)
            }
            startActivityForResult(intent, 101)
        }

        tobeConverted.setOnClickListener {
            activeText = tobeConverted
            tobeConverted.setTextColor(Color.WHITE)
            converted.setTextColor(Color.parseColor("#C9D6D4D4"))
            //activeBaseCountry = convertFromName
        }
        converted.setOnClickListener {
            activeText = converted
            converted.setTextColor(Color.WHITE)
            tobeConverted.setTextColor(Color.parseColor("#C9D6D4D4"))
            //activeBaseCountry = convertToName
        }

        //Input buttons
        val n0: Button = root.findViewById(R.id.n0)
        val n1: Button = root.findViewById(R.id.n1)
        val n2: Button = root.findViewById(R.id.n2)
        val n3: Button = root.findViewById(R.id.n3)
        val n4: Button = root.findViewById(R.id.n4)
        val n5: Button = root.findViewById(R.id.n5)
        val n6: Button = root.findViewById(R.id.n6)
        val n7: Button = root.findViewById(R.id.n7)
        val n8: Button = root.findViewById(R.id.n8)
        val n9: Button = root.findViewById(R.id.n9)
        val dot: Button = root.findViewById(R.id.dot)
        n0.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "0" else activeText.text.toString() + "0"
        }
        n1.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "1" else activeText.text.toString() + "1"
        }
        n2.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "2" else activeText.text.toString() + "2"
        }
        n4.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "4" else activeText.text.toString() + "4"
        }
        n6.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "6" else activeText.text.toString() + "6"
        }
        n7.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "7" else activeText.text.toString() + "7"
        }
        n3.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "3" else activeText.text.toString() + "3"
        }
        n8.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "8" else activeText.text.toString() + "8"
        }
        n5.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "5" else activeText.text.toString() + "5"
        }
        n9.setOnClickListener {
            activeText.text =
                if (activeText.text.toString() == "0") "9" else activeText.text.toString() + "9"
        }
        dot.setOnClickListener {
            if (!activeText.text.toString().contains(".")) activeText.text =
                activeText.text.toString() + "."
        }

        //calc buttons
        val equals: Button = root.findViewById(R.id.equals)
        equals.setOnClickListener {
            val rates: JSONObject = data.dataModel!!.getJSONObject("rates")

            val value = when {
                convertFromName.text.toString().substring(
                    0,
                    convertToName.text.toString().lastIndex - 1
                ) == data.dataModel!!.getString("base") -> {
                    rates.getDouble(
                        convertToName.text.toString().substring(
                            0,
                            convertToName.text.toString().lastIndex - 1
                        )
                    )
                }
                convertToName.text.toString().substring(
                    0,
                    convertToName.text.toString().lastIndex - 1
                ) == data.dataModel!!.getString("base") -> {
                    rates.getDouble(
                        convertFromName.text.toString().substring(
                            0,
                            convertToName.text.toString().lastIndex - 1
                        )
                    )
                }
                else -> {
                    if (activeText.id == R.id.tobeConverted) {
                        rates.getDouble(
                            convertToName.text.toString().substring(
                                0,
                                convertToName.text.toString().lastIndex - 1
                            )
                        ) / rates.getDouble(
                            convertFromName.text.toString().substring(
                                0,
                                convertToName.text.toString().lastIndex - 1
                            )
                        )
                    } else {
                        rates.getDouble(
                            convertFromName.text.toString().substring(
                                0,
                                convertToName.text.toString().lastIndex - 1
                            )
                        ) / rates.getDouble(
                            convertToName.text.toString().substring(
                                0,
                                convertToName.text.toString().lastIndex - 1
                            )
                        )
                    }
                }
            }

            Log.d(
                TAG,
                "value = $value"
            )

            if (activeText.id == R.id.tobeConverted)
                converted.text = (activeText.text.toString().toDouble() * value).toString()
            else
                tobeConverted.text =
                    (activeText.text.toString().toDouble() * value).toString()

        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            if (data?.getIntExtra("clickedBy", 1) == 1) {
                convertFromName.text = data.getStringExtra("selectedCountry")
            } else {
                convertToName.text = data?.getStringExtra("selectedCountry")
            }
        }

    }
}
