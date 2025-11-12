package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var radioGroupNumberType: RadioGroup
    private lateinit var radioEven: RadioButton
    private lateinit var radioPrime: RadioButton
    private lateinit var radioPerfect: RadioButton
    private lateinit var radioOdd: RadioButton
    private lateinit var radioSquare: RadioButton
    private lateinit var radioFibonacci: RadioButton
    private lateinit var listViewNumbers: ListView
    private lateinit var textViewEmpty: TextView

    private var numberLimit = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
        updateNumberList()
    }

    private fun initViews() {
        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroupNumberType = findViewById(R.id.radioGroupNumberType)
        radioEven = findViewById(R.id.radioEven)
        radioPrime = findViewById(R.id.radioPrime)
        radioPerfect = findViewById(R.id.radioPerfect)
        radioOdd = findViewById(R.id.radioOdd)
        radioSquare = findViewById(R.id.radioSquare)
        radioFibonacci = findViewById(R.id.radioFibonacci)
        listViewNumbers = findViewById(R.id.listViewNumbers)
        textViewEmpty = findViewById(R.id.textViewEmpty)
    }

    private fun setupListeners() {
        // Thiết lập logic chọn radio button thủ công
        val radioButtons = listOf(radioEven, radioPrime, radioPerfect, radioOdd, radioSquare, radioFibonacci)

        // Chọn mặc định
        radioEven.isChecked = true

        // Lắng nghe từng radio button
        radioButtons.forEach { radio ->
            radio.setOnClickListener {
                // Bỏ chọn tất cả các radio khác
                radioButtons.forEach { it.isChecked = false }
                // Chọn radio hiện tại
                radio.isChecked = true
                updateNumberList()
            }
        }

        // Lắng nghe thay đổi trong EditText
        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                numberLimit = s.toString().toIntOrNull() ?: 0
                updateNumberList()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateNumberList() {
        val numbers = getNumbersByType()

        if (numbers.isEmpty()) {
            listViewNumbers.visibility = View.GONE
            textViewEmpty.visibility = View.VISIBLE
        } else {
            listViewNumbers.visibility = View.VISIBLE
            textViewEmpty.visibility = View.GONE

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                numbers
            )
            listViewNumbers.adapter = adapter
        }
    }

    private fun getNumbersByType(): List<Int> {
        if (numberLimit <= 0) return emptyList()

        return when {
            radioEven.isChecked -> getEvenNumbers()
            radioPrime.isChecked -> getPrimeNumbers()
            radioPerfect.isChecked -> getPerfectNumbers()
            radioOdd.isChecked -> getOddNumbers()
            radioSquare.isChecked -> getSquareNumbers()
            radioFibonacci.isChecked -> getFibonacciNumbers()
            else -> emptyList()
        }
    }

    // Số lẻ
    private fun getEvenNumbers(): List<Int> {
        return (0 until numberLimit).filter { it % 2 == 0 }
    }

    // Số nguyên tố
    private fun getPrimeNumbers(): List<Int> {
        return (2 until numberLimit).filter { isPrime(it) }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        if (n == 2) return true
        if (n % 2 == 0) return false

        var i = 3
        while (i * i <= n) {
            if (n % i == 0) return false
            i += 2
        }
        return true
    }

    // Số hoàn hảo
    private fun getPerfectNumbers(): List<Int> {
        return (1 until numberLimit).filter { isPerfect(it) }
    }

    private fun isPerfect(n: Int): Boolean {
        if (n < 2) return false
        var sum = 1

        var i = 2
        while (i * i <= n) {
            if (n % i == 0) {
                sum += i
                if (i != n / i) {
                    sum += n / i
                }
            }
            i++
        }
        return sum == n
    }

    // Số chẵn
    private fun getOddNumbers(): List<Int> {
        return (1 until numberLimit).filter { it % 2 == 1 }
    }

    // Số chính phương
    private fun getSquareNumbers(): List<Int> {
        val result = mutableListOf<Int>()
        var i = 0
        while (i * i < numberLimit) {
            result.add(i * i)
            i++
        }
        return result
    }

    // Số Fibonacci
    private fun getFibonacciNumbers(): List<Int> {
        val result = mutableListOf<Int>()
        var a = 0
        var b = 1

        while (a < numberLimit) {
            result.add(a)
            val temp = a + b
            a = b
            b = temp
        }
        return result
    }
}