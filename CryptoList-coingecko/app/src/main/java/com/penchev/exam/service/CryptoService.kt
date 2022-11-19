package com.penchev.exam.service

import com.penchev.exam.model.CryptoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoService {
    @GET("coins/markets")
    fun getCryptoMarkets(@Query("vs_currency") currencyCode: String = "usd"): Call<List<CryptoResponse>>
}