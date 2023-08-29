package com.lhr.travel.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    fun provideOkHttpClient(): OkHttpClient{
        val httpRequestInterceptor = HttpRequestInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(httpRequestInterceptor)
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    fun getRetroInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
