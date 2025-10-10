package com.example.bt1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.graphics.drawable.GradientDrawable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber = findViewById<EditText>(R.id.etNumber)
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val llResult = findViewById<LinearLayout>(R.id.llResult)

        btnCreate.setOnClickListener {
            val input = etNumber.text.  toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra xem đầu vào có phải số không
            if (!input.matches("-?\\d+".toRegex())) {
                Toast.makeText(this, "Vui lòng nhập số hợp lệ, không được nhập chữ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val number = input.toInt()
                if (number <= 0) {
                    Toast.makeText(this, "Vui lòng nhập số lớn hơn 0!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                llResult.removeAllViews()
                llResult.visibility = LinearLayout.VISIBLE

                for (i in 1..number) {
                    val textView = TextView(this).apply {
                        text = i.toString()
                        textSize = 24f
                        gravity = android.view.Gravity.CENTER
                        val drawable = GradientDrawable()
                        drawable.setColor(android.graphics.Color.RED)
                        drawable.cornerRadius = 16f
                        background = drawable
                        setPadding(16, 8, 16, 8)
                        val params = LinearLayout.LayoutParams(900, 80)
                        params.setMargins(0, 0, 0, 20)
                        layoutParams = params
                    }
                    llResult.addView(textView)
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}