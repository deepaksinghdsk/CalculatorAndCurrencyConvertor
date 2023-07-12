package com.example.calculator.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calculator.R
import net.objecthunter.exp4j.ExpressionBuilder

class Fragment2 : Fragment(), View.OnClickListener {

    private lateinit var calcNumber: TextView
    private lateinit var result: TextView
    private var lastOperatorIndex: Int = -1
    private var fragment2tag: String = "Fragment2"
    //private var final: Int = 0
    //private var second: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment2, container, false)
        calcNumber = root.findViewById(R.id.calcNumber)
        result = root.findViewById(R.id.result)
        calcNumber.text = "0"

        //buttons with number
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
        n0.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "0" }
        n1.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "1" }
        n2.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "2" }
        n3.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "3" }
        n4.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "4" }
        n5.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "5" }
        n6.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "6" }
        n7.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "7" }
        n8.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "8" }
        n9.setOnClickListener { calcNumber.text = calcNumber.text.toString() + "9" }

        //calc buttons
        val dot: Button = root.findViewById(R.id.dot)
        val equals: Button = root.findViewById(R.id.equals)
        dot.setOnClickListener(this)
        equals.setOnClickListener(this)

        //operator buttons
        val c: Button = root.findViewById(R.id.C)
        c.setOnClickListener(this)
        val plusMinus: Button = root.findViewById(R.id.plusMinus)
        plusMinus.setOnClickListener(this)
        val percent: Button = root.findViewById(R.id.percent)
        percent.setOnClickListener(this)
        val back: Button = root.findViewById(R.id.back)
        back.setOnClickListener(this)
        val plus: Button = root.findViewById(R.id.plus)
        plus.setOnClickListener(this)
        val minus: Button = root.findViewById(R.id.minus)
        minus.setOnClickListener(this)
        val multiply: Button = root.findViewById(R.id.multiply)
        multiply.setOnClickListener(this)
        val divide: Button = root.findViewById(R.id.divide)
        divide.setOnClickListener(this)

        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dot -> {
                if (!calcNumber.text.toString().contains("."))
                    calcNumber.text = calcNumber.text.toString() + "."
            }
            R.id.C -> {
                calcNumber.text = ""
                result.text = ""
                lastOperatorIndex = -1
            }
            R.id.plusMinus -> {
                if (calcNumber.text.toString().contains("+") || calcNumber.text.toString().contains(
                        "-"
                    )
                    || calcNumber.text.toString().contains("*") || calcNumber.text.toString().contains(
                        "/"
                    )
                )
                    calcNumber.text =
                        calcNumber.text.toString().substring(0, lastOperatorIndex + 1) + "-" +
                                calcNumber.text.toString().substring(lastOperatorIndex + 1)
                else calcNumber.text = "-" + calcNumber.text.toString().substring(0)
            }
            R.id.back -> {
                if (calcNumber.text.isNotEmpty()) {
                    calcNumber.text = if (calcNumber.text.length == 1) "0"
                    else calcNumber.text.toString()
                        .substring(0, calcNumber.text.toString().lastIndex)
                    //final = Integer.parseInt(calcNumber.text.toString())
                }
            }

            R.id.percent -> {
                if (calcNumber.text.toString().last() != '%' && calcNumber.text.toString().last() != '+'
                    && calcNumber.text.toString().last() != '-'
                    && calcNumber.text.toString().last() != '/' && calcNumber.text.toString().last() != '*'
                )
                    calcNumber.text = calcNumber.text.toString() + "%"
            }
            R.id.plus -> {
                if (calcNumber.text.toString().last() != '+') {
                    calcNumber.text = calcNumber.text.toString() + "+"
                    lastOperatorIndex = calcNumber.text.lastIndex
                }
            }
            R.id.minus -> {
                if (calcNumber.text.toString().last() != '-') {
                    calcNumber.text = calcNumber.text.toString() + "-"
                    lastOperatorIndex = calcNumber.text.lastIndex
                }
            }
            R.id.multiply -> {
                if (calcNumber.text.toString().last() != '*' && calcNumber.text.toString().last() != '%'
                    && calcNumber.text.toString().last() != '/' && calcNumber.text.toString().last() != '-'
                    && calcNumber.text.toString().last() != '+'
                ) {
                    calcNumber.text = calcNumber.text.toString() + "*"
                    lastOperatorIndex = calcNumber.text.lastIndex
                }
            }
            R.id.divide -> {
                if (calcNumber.text.toString().last() != '/' && calcNumber.text.toString().last() != '*'
                    && calcNumber.text.toString().last() != '%'
                ) {
                    calcNumber.text = calcNumber.text.toString() + "/"
                    lastOperatorIndex = calcNumber.text.lastIndex
                }
            }
            R.id.equals -> {
                try {
                    val output: String =
                        ExpressionBuilder(calcNumber.text.toString()).build().evaluate().toString()
                    Log.d(fragment2tag, "Result = $output")
                    result.text = "= $output"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> Log.d(fragment2tag, "no such id found")
        }

        //result.text = "= $final"
    }
}