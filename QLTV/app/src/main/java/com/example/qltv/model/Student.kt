package com.example.qltv.model

data class Student(
    val id: Int,
    val name: String,
    // Danh sách ID sách đã mượn
    val borrowedBookIds: MutableList<Int> = mutableListOf()
)
