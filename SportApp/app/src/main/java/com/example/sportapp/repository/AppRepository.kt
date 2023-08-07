package com.example.sportapp.repository

import android.util.Log
import com.example.sportapp.data.DataOrException
import com.example.sportapp.data.Resource
import com.example.sportapp.modul.*
import com.example.sportapp.network.AppApi
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(private val api: AppApi){
    suspend fun getPredictions(data:String): DataOrException<Data, Boolean, Exception> {
        val dataOrException=DataOrException<Data,Boolean,Exception>()
        try {
            dataOrException.loading=true
            dataOrException.data= api.getPredictions("classic",data,"UEFA")
            if (dataOrException.data!!.data.isNotEmpty()) dataOrException.loading=false
        }catch (ex:java.lang.Exception){
            dataOrException.e=ex
        }
        return dataOrException
    }


    suspend fun getHeadToHead(id:Int): Resource<Stats> {
        val response=try {
            Resource.Loading(data = true)
            api.getHeadToHead(id,"10")
        }catch (ex:Exception){
            Log.d("ZAZAAZ","$ex")
            return Resource.Error(message = ex.toString())
        }
        Resource.Loading(data = false)
        Log.d("ZAZAAssZ","$response")

        return Resource.Success(data = response)
    }

    suspend fun getPreditionDetails(id:Int):Resource<PredictionDetails>{
        val response = try {
            Resource.Loading(data = true)
            api.getPredictionDetails(id)
        }catch (ex:java.lang.Exception){
            return Resource.Error(message = ex.toString())
        }
        Resource.Loading(false)
        return Resource.Success(response)
    }

    suspend fun getApiPerfomance(): Resource<PerfomanceDetails>{
        val response=try {
            Resource.Loading(data = true)
            api.getPerfomanceDetails()
        }catch (ex:Exception){
            return Resource.Error(message = ex.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }

}