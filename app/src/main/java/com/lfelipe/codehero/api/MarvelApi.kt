package com.lfelipe.codehero.api

import com.lfelipe.codehero.model.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun characters(
        @Query("offset") off : Int,
        @Query("limit") limit : Int = 4
    ): Response<Characters>

}