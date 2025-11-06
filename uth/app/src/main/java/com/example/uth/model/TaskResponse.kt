package com.example.uth.model

data class TaskResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)
data class TaskDetailResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: Task
)
