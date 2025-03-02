package com.example.calculator_umb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator_umb.databinding.ActivityMainBinding

class MainCalculator : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentExpression = ""
    private var newNumberInput = ""
    private var operator: String? = null
    private var firstNumberInput: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNumbersListeners()
        setOperatorListeners()
        setResultEqualOrCleanListeners()
    }

    private fun setNumbersListeners() {
        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                newNumberInput += button.text.toString()
                currentExpression += button.text.toString()
                binding.textView.text = currentExpression
            }
        }
    }

    private fun setOperatorListeners() {
        val operators = mapOf(
            binding.btnPlus to "+",
            binding.btnMinus to "-",
            binding.btnMultiply to "*",
            binding.btnDivide to "/"
        )

        operators.forEach { (button, op) ->
            button.setOnClickListener {
                if (newNumberInput.isNotEmpty()) {
                    firstNumberInput = newNumberInput.toInt()
                    operator = op
                    newNumberInput = ""
                    currentExpression += " $op " // Agregar operador a la pantalla
                    binding.textView.text = currentExpression
                }
            }
        }
    }

    private fun setResultEqualOrCleanListeners() {
        binding.btnClear.setOnClickListener {
            currentExpression = ""
            newNumberInput = ""
            firstNumberInput = null
            operator = null
            binding.textView.text = ""
        }

        binding.btnEquals.setOnClickListener {
            if (firstNumberInput != null && operator != null && newNumberInput.isNotEmpty()) {
                val secondNumberInput = newNumberInput.toInt()
                val result = when (operator) {
                    "+" -> firstNumberInput!! + secondNumberInput
                    "-" -> firstNumberInput!! - secondNumberInput
                    "*" -> firstNumberInput!! * secondNumberInput
                    "/" -> if (secondNumberInput != 0) firstNumberInput!! / secondNumberInput else "Error"
                    else -> ""
                }
                binding.textView.text = "$currentExpression = $result"
                currentExpression = result.toString()
                newNumberInput = result.toString()
                firstNumberInput = null
                operator = null
            }
        }
    }
}
