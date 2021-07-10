package com.lfelipe.codehero.api

import com.lfelipe.codehero.util.Constants.Api.BASE_URL
import com.lfelipe.codehero.util.Constants.Api.PRIVATE_KEY
import com.lfelipe.codehero.util.Constants.Api.PUBLIC_KEY
import com.lfelipe.codehero.util.md5
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {

    val marvelApi = getMarvelApiClient().create(MarvelApi::class.java)


    private fun getInterceptorClient(): OkHttpClient {
        val timeStamp = System.currentTimeMillis().toString()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->

                val newRequest = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("apikey", PUBLIC_KEY)
                    .addQueryParameter("hash", "$timeStamp$PRIVATE_KEY$PUBLIC_KEY".md5())
                    .build()


                chain.proceed(chain.request().newBuilder().url(newRequest).build())

            }
        return interceptor.build()
    }

    private fun getMarvelApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptorClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}