package com.example.bt3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        checkButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val result = when {
                email.isEmpty() -> "Email không hợp lệ"
                !email.contains("@") -> "Email không đúng định dạng"
                else -> "bạn đã nhập Email hợp lệ"
            }
            resultText.text = result
            resultText.setTextColor(if (result == "bạn đã nhập Email hợp lệ") 0xFF00FF00.toInt() else 0xFFFF0000.toInt())
        }
    }
}