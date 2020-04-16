package com.hellodiffa.multipart_request_progressbar.remote.api

import com.hellodiffa.multipart_request_progressbar.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BaseApi {

    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val req = chain.request()
                .newBuilder()
                .addHeader(
                    "api-token",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODgxNjYzNzMsImlhdCI6MTU4Njk1Njc3Mywic3ViIjoyfQ.1vxkqSQn4XGJbfIaG7X9qfSr1biLs1LqHrOSdidaQSw"
                )
                .build()
            return@addInterceptor chain.proceed(req)
        }
        .build()

    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://freecoba.herokuapp.com/")
        .build()

    fun createService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}