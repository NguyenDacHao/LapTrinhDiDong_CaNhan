package com.example.bt2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        checkButton.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull() ?: 0

            val category = when {
                name.isEmpty() -> "Vui lòng nhập tên!"
                age > 65 -> "$name, bạn là Người già"
                age in 6..65 -> "$name, bạn là Người lớn"
                age in 2..5 -> "$name, bạn là Trẻ em"
                age in 1..1 -> "$name, bạn là Em bé"
                else -> "Tuổi không hợp lệ"
            }

            resultText.text = category
        }
    }
}