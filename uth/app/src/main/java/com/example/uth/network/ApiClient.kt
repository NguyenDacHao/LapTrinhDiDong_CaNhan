package com.example.uth.network

import com.example.uth.model.Task
import com.example.uth.model.TaskDetailResponse
import com.example.uth.model.TaskResponse
import retrofit2.http.*

interface TaskApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): TaskResponse


    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): TaskDetailResponse

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}

object ApiClient {
    private const val BASE_URL = "https://amock.io/api/"

    val instance: TaskApiService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
}
