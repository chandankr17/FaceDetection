package com.example.facedetection

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header

data class SignupRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val name: String, val userId: String)
data class SaveResultRequest(val image: String, val smile: Int, val leftEye: Int, val rightEye: Int)
data class ResultResponse(val _id: String, val smile: Int, val leftEye: Int, val rightEye: Int, val createdAt: String)

interface ApiService {

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("results/save")
    suspend fun saveResult(
        @Header("authorization") token: String,
        @Body request: SaveResultRequest
    ): Map<String, String>

    @GET("results/history")
    suspend fun getHistory(
        @Header("authorization") token: String
    ): List<ResultResponse>
}