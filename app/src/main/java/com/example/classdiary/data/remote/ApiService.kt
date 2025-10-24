package com.example.classdiary.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val email: String, val senha: String)
data class TokenResponse(val accessToken: String)
data class MeResponse(val nome: String, val email: String)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @GET("auth/me")
    suspend fun me(): MeResponse
}