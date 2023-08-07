package com.example.sportapp.network

import com.example.sportapp.modul.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface AppApi {
    @GET("predictions")
    suspend fun getPredictions(@Query("market") market:String,
                               @Query("iso-date") iso_date:String,
                               @Query("federation") federation:String): Data


    @GET("head-to-head/{id}")
    suspend fun getHeadToHead(@Path("id") id:Int, @Query("limit") limit:String): Stats


    @GET("predictions/{id}")
    suspend fun getPredictionDetails(@Path("id") id:Int):PredictionDetails

    @GET("performance-stats")
    suspend fun getPerfomanceDetails():PerfomanceDetails
}