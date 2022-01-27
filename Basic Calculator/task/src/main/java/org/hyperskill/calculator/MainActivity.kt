package org.hyperskill.calculator

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var first = "0"
        var sign = "+"

        addButton.setOnClickListener {
            vibro()
            checkError()
            first = editText.text.toString()
            editText.hint = first
            editText.setText("")
            sign = "+"
        }
        subtractButton.setOnClickListener {
            vibro()
            checkError()
            if (editText.text.toString() == "0") {
                editText.setText("-")
            } else {
                first = editText.text.toString()
                editText.hint = first
                editText.setText("")
                sign = "-"
            }
        }
        multiplyButton.setOnClickListener {
            vibro()
            checkError()
            first = editText.text.toString()
            editText.hint = first
            editText.setText("")
            sign = "x"
        }
        divideButton.setOnClickListener {
            vibro()
            checkError()
            first = editText.text.toString()
            editText.hint = first
            editText.setText("")
            sign = "/"
        }
        equalButton.setOnClickListener {
            vibro()
            val currentText = editText.text.toString()
            when (sign) {
                "+" -> {
                    val calcPlus = if (editText.text.isEmpty()) calc(first, "0", sign)
                    else calc(first, currentText, sign)
                    editText.setText(calcPlus)
                    first = calcPlus
                }
                "-" -> {
                    val calcMinus = if (editText.text.isEmpty()) calc(first, "0", sign)
                    else calc(first, currentText, sign)
                    editText.setText(calcMinus)
                    first = calcMinus
                }
                "x" -> {
                    val calcMult = if (editText.text.isEmpty()) calc(first, "1", sign)
                    else calc(first, currentText, sign)
                    editText.setText(calcMult)
                    first = calcMult
                }
                "/" -> {
                    val calcDiv = if (editText.text.isEmpty()) calc(first, "1", sign)
                    else calc(first, currentText, sign)
                    editText.setText(calcDiv)
                    first = if (calcDiv == "Error") "0" else calcDiv
                }
            }
        }
        clearButton.setOnClickListener {
            vibro()
            editText.setText("0")
            editText.hint = "0"
            first = "0"
        }
        button0.setOnClickListener {
            vibro()
            val text = editText.text.toString()
            if (text != "0" && text != "-") editText.append("0")
        }
        button1.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("1") else editText.append("1")
        }
        button2.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("2") else editText.append("2")
        }
        button3.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("3") else editText.append("3")
        }
        button4.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("4") else editText.append("4")
        }
        button5.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("5") else editText.append("5")
        }
        button6.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("6") else editText.append("6")
        }
        button7.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("7") else editText.append("7")
        }
        button8.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("8") else editText.append("8")
        }
        button9.setOnClickListener {
            vibro()
            if (editText.text.toString() == "0") editText.setText("9") else editText.append("9")
        }
        dotButton.setOnClickListener {
            vibro()
            if (editText.text.toString() == "-") editText.append("0.")
            if (!editText.text.toString().contains(".")) editText.append(".")
        }
    }
    private fun calc(number1: String, number2: String, sign: String): String {
        val big1 = number1.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
        val big2 = number2.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
        if (big2 == BigDecimal("0.00") && sign == "/") return "Error"

        var result = when (sign) {
            "+" -> big1 + big2
            "-" -> big1 - big2
            "x" -> big1 * big2
            else -> big1 / big2
        }

        result = if (isWholeNumber(result)) result.setScale(0, RoundingMode.HALF_EVEN)
        else result.setScale(1, RoundingMode.HALF_EVEN)

        return result.toString()
    }
    private fun isWholeNumber(number: BigDecimal): Boolean {
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
    }
    private fun checkError() {
        if (editText.text.toString() == "Error") {
            editText.setText("0")
        }
    }
    private fun vibro() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(33, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(33) // Vibrate method for below API Level 26
            }
        }
    }
}
