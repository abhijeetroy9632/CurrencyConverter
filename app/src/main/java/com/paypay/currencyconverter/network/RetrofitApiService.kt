package com.paypay.currencyconverter.network

import com.paypay.currencyconverter.model.LiveExchangeRatesEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("live")
    fun fetchLiveExchangeRates(@Query("access_key") apiKey: String): Single<LiveExchangeRatesEntity>
}