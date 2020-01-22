package com.developer.anishakd4.halodocassignment.Networking

import com.developer.anishakd4.halodocassignment.Model.NewsModel
import retrofit2.Response
import retrofit2.http.GET

interface GetDataInterface {

    @GET("api/v1/search?query=sports")
    suspend fun getNews(): Response<NewsModel>
}