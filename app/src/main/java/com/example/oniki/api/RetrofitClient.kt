package com.example.oniki.api

import android.util.Base64
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {


    private val AUTH = "Basic " + android.util.Base64.encodeToString(
        "belalkhan:123456".toByteArray(),
        Base64.NO_WRAP
    )

    private const val BASE_URL = "https://api.oniki.mgsapp.com/api/"

    var gson = GsonBuilder()
        .setLenient()
        .create()
    var logging = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Accept", "application/vnd.api+json")
                .addHeader("Accept-Language", "en")
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()


    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}