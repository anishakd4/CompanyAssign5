package com.developer.anishakd4.halodocassignment.Networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetDataService {

    private val BASE_URL = "https://hn.algolia.com";

    fun getGetDataService(): GetDataInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetDataInterface::class.java)
    }

}