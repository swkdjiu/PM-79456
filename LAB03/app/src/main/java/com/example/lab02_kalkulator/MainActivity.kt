package com.example.lab02_kalkulator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "CalculatorDebug" // Tag do filtrowania w Logcat

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var firstValue = 0.0
    private var currentOperator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Aplikacja została uruchomiona.")
        tvResult = findViewById(R.id.tvResult)

        // 1. Obsługa przycisków cyfr (0-9)
        val numberButtons = listOf(
            Pair(R.id.btn0, "0"),
            Pair(R.id.btn1, "1"),
            Pair(R.id.btn2, "2"),
            Pair(R.id.btn3, "3"),
            Pair(R.id.btn4, "4"),
            Pair(R.id.btn5, "5"),
            Pair(R.id.btn6, "6"),
            Pair(R.id.btn7, "7"),
            Pair(R.id.btn8, "8"),
            Pair(R.id.btn9, "9")
        )

        for ((id, digit) in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                currentInput += digit
                tvResult.text = currentInput
                Log.d(TAG, "Kliknięto cyfrę: $digit, aktualny input: $currentInput")
            }
        }

        // 2. Przycisk czyszczenia (C)
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentInput = ""
            firstValue = 0.0
            currentOperator = ""
            tvResult.text = "0"
            isNewOperation = true
            Log.d(TAG, "Wyczyszczono kalkulator (Reset).")
        }

        // 3. Obsługa operatorów arytmetycznych (+, *, /)
        val operatorButtons = listOf(
            Pair(R.id.btnPlus, "+"),
            Pair(R.id.btnMultiply, "*"),
            Pair(R.id.btnDivide, "/")
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    firstValue = currentInput.toDouble()
                    currentOperator = op
                    isNewOperation = true
                    Log.d(TAG, "Wybrano operator: $op, zapamiętana wartość: $firstValue")
                }
            }
        }

        // 4. Przycisk równa się (=) – obliczenia i wynik
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                val secondValue = currentInput.toDouble()
                Log.d(TAG, "Wykonywanie obliczenia: $firstValue $currentOperator $secondValue")

                val result = when (currentOperator) {
                    "+" -> firstValue + secondValue
                    "*" -> firstValue * secondValue
                    "/" -> if (secondValue != 0.0) firstValue / secondValue else 0.0
                    else -> 0.0
                }

                tvResult.text = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
                Log.d(TAG, "Wynik operacji: ${tvResult.text}")

                currentInput = tvResult.text.toString()
                currentOperator = ""
                isNewOperation = true
            }
        }
    }
}