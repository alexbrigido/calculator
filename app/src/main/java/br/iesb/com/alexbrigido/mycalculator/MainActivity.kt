package br.iesb.com.alexbrigido.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    var history: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOne.setOnClickListener{ appendOnExpression("1", true) }
        tvTwo.setOnClickListener{ appendOnExpression("2", true) }
        tvThree.setOnClickListener{ appendOnExpression("3", true) }
        tvFour.setOnClickListener{ appendOnExpression("4", true) }
        tvFive.setOnClickListener{ appendOnExpression("5", true) }
        tvSix.setOnClickListener{ appendOnExpression("6", true) }
        tvSeven.setOnClickListener{ appendOnExpression("7", true) }
        tvEight.setOnClickListener{ appendOnExpression("8", true) }
        tvNine.setOnClickListener{ appendOnExpression("9", true) }
        tvZero.setOnClickListener{ appendOnExpression("0", true) }
        tvDot.setOnClickListener{ appendOnExpression(".", true) }

        tvPlus.setOnClickListener{ appendOnExpression("+", false) }
        tvMinus.setOnClickListener{ appendOnExpression("-", false) }
        tvMult.setOnClickListener{ appendOnExpression("*", false) }
        tvDivide.setOnClickListener{ appendOnExpression("/", false) }
        tvOpen.setOnClickListener{ appendOnExpression("(", false) }
        tvClose.setOnClickListener{ appendOnExpression(")", false) }

        tvInverseTangent.setOnClickListener{
            if(validateExpression()) {
                var tangent = tan(tvExpression.text.toString().toDouble())
                var cotangent = 1.0 / tangent
                tvResult.text = cotangent.toString()
                appendOnHistory("1/tan")
            }
        }

        tvNaturalLog.setOnClickListener{
            if(validateExpression()){
                tvResult.text = ln(tvExpression.text.toString().toDouble()).toString()
                appendOnHistory("ln")
            }
        }

        tvChangeSignal.setOnClickListener{
            if(validateExpression()) {
                tvResult.text = (tvExpression.text.toString().toDouble() * -1.0).toString()
                appendOnHistory("-1")
            }
        }

        tvCosine.setOnClickListener{
            if(validateExpression()) {
                tvResult.text = cos(tvExpression.text.toString().toDouble()).toString()
                appendOnHistory("cos")
            }
        }

        tvTangent.setOnClickListener{
            if(validateExpression()) {
                tvResult.text = tan(tvExpression.text.toString().toDouble()).toString()
                appendOnHistory("tan")
            }
        }

        tvInverseSine.setOnClickListener {
            if(validateExpression()) {
                var sine = sin(tvExpression.text.toString().toDouble())
                var inverseSine = 1.0 / sine
                tvResult.text = inverseSine.toString()
                appendOnHistory("!sen")
            }
        }

        tvInverseCosine.setOnClickListener{
            if(validateExpression()) {
                var cosine = sin(tvExpression.text.toString().toDouble())
                var inverseCosine = 1.0 / cosine
                tvResult.text = inverseCosine.toString()
                appendOnHistory("!cos")
            }
        }

        tvPow.setOnClickListener{
            if(validateExpression()) {
                tvResult.text = tvExpression.text.toString().toDouble().pow(2.0).toString()
                appendOnHistory("n^2")
            }
        }

        tvInverse.setOnClickListener {
            if(validateExpression()) {
                val result = 1.0 / tvExpression.text.toString().toDouble()
                tvResult.text = result.toString()
                appendOnHistory("1/n")
            }
        }

        tvLog.setOnClickListener {
            if(validateExpression()) {
                tvResult.text = log(tvExpression.text.toString().toDouble(), 10.0).toString()
                appendOnHistory("log")
            }
        }

        tvSine.setOnClickListener {
            if(validateExpression()) {
                tvResult.text = sin(tvExpression.text.toString().toDouble()).toString()
                appendOnHistory("sen")
            }
        }

        tvSquare.setOnClickListener{
            if(validateExpression()) {
                tvResult.text = sqrt(tvExpression.text.toString().toDouble()).toString()
                appendOnHistory("sqrt")
            }
        }

        tvModulus.setOnClickListener{
            if(validateExpression()) {
                appendOnExpression("%", false)
                appendOnHistory("mod")
            }
        }

        tvFactorial.setOnClickListener{
            if(validateExpression()) {
                val result = factorial(tvExpression.text.toString().toLong())
                tvResult.text = result.toString()
                appendOnHistory("!")
            }
        }

        tvPower.setOnClickListener {
            if(validateExpression()) {
                appendOnExpression("^", false)
                appendOnHistory("^")
            }
        }

        tvClear.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        tvBack.setOnClickListener {
            val string = tvExpression.text.toString()
            if(string.isNotEmpty()){
                tvExpression.text = string.substring(0, string.length-1)
            }
            tvResult.text = ""
        }

        tvEquals.setOnClickListener {
            try {
                val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble()) {
                    tvResult.text = longResult.toString()
                } else {
                    tvResult.text = result.toString()
                }
                appendOnHistory("")
            }catch (e:Exception){
                Log.d("Exception"," message : " + e.message )
            }
        }
    }

    private fun appendOnExpression(operation: String, canClear: Boolean){
        if(tvResult.text.isNotEmpty()){
            tvExpression.text = "";
        }
        if(canClear){
            tvResult.text = ""
            tvExpression.append(operation)
        }else{
            tvExpression.append(tvResult.text)
            tvExpression.append(operation)
            tvResult.text = ""
        }
    }

    private fun factorial(number: Long): Long {
        var factorial: Long = 1
        for(i in 1..number){
            factorial *= i.toLong()
        }
        return factorial
    }

    private fun appendOnHistory(operation: String){
        tvHistory.text = ""
        var lines = ""
        if(history.size > 10){
            history.removeFirst()
        }
        history.add( operation + "(" + tvExpression.text.toString()  + ") = " + tvResult.text.toString())
        for(i in 0 until history.size){
            lines += history[i] + "\n"
        }
        tvHistory.text = lines
    }

    private fun validateExpression(): Boolean {
        if(tvExpression.text.toString().isNullOrEmpty()){
            val toast = Toast.makeText(applicationContext, "Pârametro inválido!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 200, 200)
            toast.show()
            return false
        }
        return true
    }
}